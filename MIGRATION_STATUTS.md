# Migration des Statuts - Modèle First Delivery/Navex

## 📋 Résumé

Le backend BFExpress a été ajusté pour supporter un vrai cycle de vie de colis type First Delivery/Navex (transporteur B2B Tunisie, COD, manifeste, enlèvement, dépôt, livraison, retours).

## ✅ Modifications Backend

### 1. Enums

#### `StatutCommande` (commandes-service)
**Ancien :** EN_ATTENTE, CONFIRMEE, EN_LIVRAISON, LIVREE, ANNULEE
**Nouveau :**
```java
EN_ATTENTE          // créée par l'expéditeur, pas encore sur manifeste validé
MANIFESTE           // incluse dans un manifeste (brouillon validé ou manifeste créé)
A_ENLEVER           // planifiée pour enlèvement chez l'expéditeur
ENLEVE              // enlevée chez l'expéditeur
AU_DEPOT            // arrivée au hub / dépôt
EN_LIVRAISON        // sortie en tournée livreur
LIVRE               // livrée avec succès (cash éventuellement à confirmer)
LIVRE_PAYE          // livrée + COD encaissé
ECHEC_LIVRAISON     // destinataire absent / refus / report
RETOUR_DEPOT        // retourné au dépôt
RETOUR_EXPEDITEUR   // rendu à l'expéditeur
ANNULEE             // annulée (uniquement vraie annulation, PAS les retours)
```

#### `Colis` entity
- Remplacement de `StatutColis` par `StatutCommande` pour un tracking unifié
- Unification des statuts entre Commande et Colis

### 2. Nouveau Service

#### `StatutTransitionService`
Service de validation des transitions de statut selon les règles métier :

**Transitions autorisées :**
- EN_ATTENTE → MANIFESTE | A_ENLEVER | ANNULEE
- MANIFESTE → A_ENLEVER | ANNULEE
- A_ENLEVER → ENLEVE | ANNULEE
- ENLEVE → AU_DEPOT | EN_LIVRAISON
- AU_DEPOT → EN_LIVRAISON | RETOUR_EXPEDITEUR
- EN_LIVRAISON → LIVRE | LIVRE_PAYE | ECHEC_LIVRAISON | RETOUR_DEPOT
- ECHEC_LIVRAISON → EN_LIVRAISON | RETOUR_DEPOT | RETOUR_EXPEDITEUR
- RETOUR_DEPOT → RETOUR_EXPEDITEUR | EN_LIVRAISON (re-tentative)
- LIVRE → LIVRE_PAYE
- LIVRE_PAYE / RETOUR_EXPEDITEUR / ANNULEE → terminal

Toute transition interdite renvoie une erreur 400 avec message explicite.

### 3. Services Modifiés

#### `CommandeServiceImpl`
- `updateStatut()` : Ajout validation des transitions via `StatutTransitionService`

#### `ColisServiceImpl`
- `updateStatut()` : Nouvelle méthode avec validation des transitions
- Alignement sur `StatutCommande` au lieu de `StatutColis`

#### `ManifesteServiceImpl`
- `validerManifeste()` : Change BROUILLON → IMPRIME
- **NOUVEAU** : Met à jour les statuts des colis associés : EN_ATTENTE/MANIFESTE → A_ENLEVER

### 4. Controllers Modifiés

#### `ColisController`
- Ajout endpoint `PATCH /api/colis/{id}/statut?statut=` pour mise à jour statut colis

### 5. Migration Base de Données

**Script :** `commandes-service/src/main/resources/db/migration/V1__migrate_statuts.js`

**Mapping des anciennes valeurs :**

| Ancien Statut Commande | Nouveau Statut |
|------------------------|----------------|
| EN_ATTENTE | EN_ATTENTE |
| CONFIRMEE | MANIFESTE |
| EN_LIVRAISON | EN_LIVRAISON |
| LIVREE | LIVRE |
| ANNULEE | ANNULEE |

| Ancien Statut Colis | Nouveau Statut |
|---------------------|----------------|
| EN_ATTENTE | EN_ATTENTE |
| NON_SERIEUX | ANNULEE |
| A_VERIFIER | EN_ATTENTE |
| A_ENLEVER | A_ENLEVER |
| ENLEVE | ENLEVE |
| AU_DEPOT | AU_DEPOT |
| RETOUR_DEPOT | RETOUR_DEPOT |
| EN_TRANSIT | EN_LIVRAISON |
| LIVRE | LIVRE |
| LIVRE_PAYE | LIVRE_PAYE |
| ECHANGE | RETOUR_DEPOT |
| REMBOURSE | RETOUR_EXPEDITEUR |

**Exécution :**
```bash
mongosh bfexpress commandes-service/src/main/resources/db/migration/V1__migrate_statuts.js
```

## 🎯 Instructions pour le Frontend

### ⚠️ IMPORTANT : PLUS BESOIN DE MAPPING

Les statuts backend sont maintenant **IDENTIQUES** aux statuts frontend. Supprimez tout mapping dans le code Angular.

### Actions à effectuer dans `dashboard-client.component.ts` :

1. **Supprimer le mapping de statuts** dans `getStatusClass()` et `getStatusLabel()`
2. **Utiliser directement les statuts** du backend
3. **Mettre à jour `validerEtImprimerManifest()`** pour utiliser le nouveau comportement :
   - Le backend passe automatiquement les colis à A_ENLEVER lors de la validation du manifeste
   - Plus besoin de boucle `forEach` pour mettre à jour les statuts manuellement

### Exemple de code simplifié :

```typescript
// Ancien code (à supprimer)
getStatusClass(statut: string): string {
  if (!statut) return 'status-en_attente';
  const s = statut.toLowerCase();
  if (s.includes('livre')) return 'status-livree';
  // ... mapping complexe
}

// Nouveau code (direct)
getStatusClass(statut: string): string {
  return `status-${statut?.toLowerCase() || 'en_attente'}`;
}
```

### Statuts utilisables dans le frontend :

```typescript
detailedStatuses = [
  { key: 'EN_ATTENTE', label: 'En attente' },
  { key: 'MANIFESTE', label: 'Sur manifeste' },
  { key: 'A_ENLEVER', label: 'À enlever' },
  { key: 'ENLEVE', label: 'Enlevé' },
  { key: 'AU_DEPOT', label: 'Au dépôt' },
  { key: 'EN_LIVRAISON', label: 'En livraison' },
  { key: 'LIVRE', label: 'Livré' },
  { key: 'LIVRE_PAYE', label: 'Livré & payé' },
  { key: 'ECHEC_LIVRAISON', label: 'Échec livraison' },
  { key: 'RETOUR_DEPOT', label: 'Retour dépôt' },
  { key: 'RETOUR_EXPEDITEUR', label: 'Retour expéditeur' },
  { key: 'ANNULEE', label: 'Annulée' }
];
```

## 🧪 Tests Manuels

### 1. Créer une commande
```bash
POST /api/commandes
{
  "clientId": "...",
  "adresseDepartId": "...",
  "adresseArriveeId": "...",
  "typeService": "STANDARD",
  "colis": [...]
}
```
**Résultat attendu :** statut = EN_ATTENTE

### 2. Valider un manifeste
```bash
PATCH /api/manifestes/{id}/valider
```
**Résultat attendu :**
- Manifeste statut = IMPRIME
- Colis associés : EN_ATTENTE/MANIFESTE → A_ENLEVER

### 3. Flux de livraison complet
```bash
# Enlèvement
PATCH /api/colis/{id}/statut?statut=ENLEVE

# Arrivée dépôt
PATCH /api/colis/{id}/statut?statut=AU_DEPOT

# En livraison
PATCH /api/colis/{id}/statut?statut=EN_LIVRAISON

# Livraison réussie
PATCH /api/colis/{id}/statut?statut=LIVRE

# Confirmation COD
PATCH /api/colis/{id}/statut?statut=LIVRE_PAYE
```

### 4. Test transition interdite
```bash
PATCH /api/colis/{id}/statut?statut=EN_ATTENTE
```
**Résultat attendu :** Erreur 400 avec message explicite

### 5. Flux retour
```bash
# Échec livraison
PATCH /api/colis/{id}/statut?statut=ECHEC_LIVRAISON

# Retour dépôt
PATCH /api/colis/{id}/statut?statut=RETOUR_DEPOT

# Retour expéditeur
PATCH /api/colis/{id}/statut?statut=RETOUR_EXPEDITEUR
```

## 📊 Endpoints Impactés

| Endpoint | Changement |
|----------|------------|
| `PATCH /api/commandes/{id}/statut` | Validation des transitions |
| `PATCH /api/colis/{id}/statut` | **NOUVEAU** + validation des transitions |
| `PATCH /api/manifestes/{id}/valider` | Met à jour les statuts des colis associés |
| `POST /api/commandes` | Statut initial = EN_ATTENTE (inchangé) |
| `GET /api/paiements/client/{id}/reglements` | Basé sur LIVRE et LIVRE_PAYE |

## 🎉 Conclusion

Le backend est maintenant aligné sur le modèle First Delivery/Navex avec :
- ✅ Statuts détaillés et cohérents
- ✅ Validation stricte des transitions
- ✅ Automatisation du workflow manifeste
- ✅ Migration des données existantes
- ✅ Frontend peut utiliser les statuts à l'identique (plus de mapping)

**Next step pour le frontend :** Supprimer tout mapping de statuts et utiliser directement les valeurs du backend.
