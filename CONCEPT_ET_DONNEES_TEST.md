# Concept du Site BFExpress et Données de Test

## 🎯 Concept du Site

**BFExpress** est une plateforme de livraison tunisienne complète qui permet :

### Pour les Clients :
- ✅ Créer des commandes de livraison
- ✅ Suivre leurs colis en temps réel
- ✅ Gérer des manifestes (groupes de colis)
- ✅ Planifier des enlèvements
- ✅ Effectuer des réclamations
- ✅ Consulter leurs paiements
- ✅ Gérer des adresses (personnelles et professionnelles)

### Pour les Admins :
- ✅ Gérer les utilisateurs (approbation)
- ✅ Affecter automatiquement les livreurs via IA
- ✅ Affecter manuellement les livreurs
- ✅ Superviser toutes les commandes
- ✅ Gérer les livreurs et dépôts

### Pour les Livreurs :
- ✅ Voir leurs livraisons assignées
- ✅ Démarrer/terminer les livraisons
- ✅ Mettre à jour leur position GPS
- ✅ Gérer leur statut (disponible/en course/hors ligne)

## 🗄️ Données de Test Créées

### 🔴 Service Authentification (Port 8082)

**Comptes créés :**
- **Admin** : admin@bfexpress.com / admin123
- **Client** : client@bfexpress.com / client123 (avec infos pro : BFExpress SARL)
- **Livreur** : livreur@bfexpress.com / livreur123

### 🔵 Service Commandes (Port 8081)

**Adresses créées :**
- Adresse client 1 : Tunis (123 Rue de la Liberté)
- Adresse client 2 : Sfax (45 Avenue Habib Bourguiba)
- Adresse départ : Centre de Tri BFExpress Tunis

**Destinataires créés :**
- Ahmed Ben Ali (Tunis)
- Fatma Trabelsi (Sfax)
- Mohamed Karray (Tunis)

**Commandes créées :**
1. **EN_ATTENTE** - Standard, 25.0 DT, 45 min estimé
2. **EN_LIVRAISON** - Express, 35.0 DT, 30 min estimé
3. **LIVREE** - Standard, 28.0 DT, 60 min estimé
4. **CONFIRMEE** - Standard, 30.0 DT, 50 min estimé

**Colis créés :**
- 6 colis avec différents statuts : EN_ATTENTE, EN_TRANSIT, LIVRE, A_ENLEVER, AU_DEPOT
- Certains rattachés à des commandes, d'autres autonomes

**Manifeste créé :**
- 1 manifeste avec 3 colis (statut BROUILLON)

**Enlèvement créé :**
- 1 enlèvement planifié pour le lendemain

### 🟢 Service Tracking (Port 8083)

**Livraisons créées :**
1. **EN_COURS** - Livraison active avec tracking GPS
2. **AFFECTEE** - En attente de démarrage
3. **LIVREE** - Terminée avec succès
4. **ECHOUEE** - Livraison échouée

**Positions GPS créées :**
- 6 positions GPS pour tracking en temps réel
- Positions pour la livraison en cours (4 points)
- Positions pour la livraison livrée (2 points)

### 🟣 Service Livreurs (Port 8084)

**Dépôts créés :**
- Dépôt Central Tunis (capacité 1000)
- Dépôt Sfax (capacité 500)

**Livreurs créés :**
1. **Ahmed Ben** - DISPONIBLE, note 4.8/5
2. **Fatma Trabelsi** - EN_COURSE, note 4.9/5
3. **Mohamed Karray** - HORS_LIGNE, note 4.5/5

## 🚀 Comment Démarrer

### 1. Vider MongoDB (IMPORTANT)
```bash
mongosh
use livraison_auth
db.utilisateur.deleteMany({})
exit
```

### 2. Démarrer les services dans l'ordre :
1. **Auth Service** (port 8082) - Crée les comptes utilisateurs
2. **Commandes Service** (port 8081) - Crée les commandes et colis
3. **Tracking Service** (port 8083) - Crée les livraisons et positions GPS
4. **Livreurs Service** (port 8084) - Crée les livreurs et dépôts
5. **IA Service** (port 8086) - Optionnel
6. **Notifications Service** (port 8086) - Optionnel
7. **Audit Service** (port 8087) - Optionnel

### 3. Démarrer le frontend
```bash
cd F:\stage_timsoft_frontend\BFExpress
npm start
```

### 4. Tester les connexions

**Client** : client@bfexpress.com / client123
- Voir les commandes avec différents statuts
- Créer de nouvelles commandes
- Voir le manifeste et les enlèvements

**Admin** : admin@bfexpress.com / admin123
- Voir toutes les commandes
- Affecter des livreurs (manuel ou IA)
- Gérer les utilisateurs

**Livreur** : livreur@bfexpress.com / livreur123
- Voir les livraisons assignées
- Démarrer/terminer les livraisons
- Mettre à jour le GPS

## 📊 Scénarios de Test

### Scénario 1 : Client crée une commande
1. Connectez-vous en tant que client
2. Allez dans l'onglet "Ajout Colis"
3. Remplissez le formulaire
4. La commande apparaît dans le dashboard avec statut EN_ATTENTE

### Scénario 2 : Admin affecte un livreur
1. Connectez-vous en tant qu'admin
2. Voyez la commande EN_ATTENTE
3. Cliquez sur "Affecter IA" pour affectation automatique
4. Ou sélectionnez manuellement un livreur

### Scénario 3 : Livreur effectue la livraison
1. Connectez-vous en tant que livreur
2. Voyez la livraison affectée
3. Cliquez sur "Démarrer" pour commencer
4. Mettez à jour le GPS
5. Cliquez sur "Terminer" pour finir

### Scénario 4 : Client suit son colis
1. Connectez-vous en tant que client
2. Voyez la commande EN_LIVRAISON
3. Suivez la position GPS en temps réel

## 🔧 Adaptation Frontend

Le frontend Angular est déjà configuré pour utiliser ces données de test :

- **Dashboard Client** : Affiche les commandes avec différents statuts
- **Dashboard Admin** : Gestion des commandes et affectations
- **Dashboard Livreur** : Gestion des livraisons et GPS

Les données de test sont réalistes et montrent tous les fonctionnalités de la plateforme BFExpress !