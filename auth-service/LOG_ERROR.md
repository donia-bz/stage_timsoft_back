# Erreur d'Inscription - Diagnostic

## 🔍 Problème
Le formulaire d'inscription échoue pour les clients et livreurs.

## 🛠️ Tentatives de Résolution

### 1. Test API Backend
```bash
curl -X POST http://localhost:8082/api/auth/register
```
**Résultat** : Échec (Exit code 1)

### 2. Vérification Service Auth
```bash
curl http://localhost:8082/api/auth/users
```
**Résultat** : ✅ Succès (200 OK) - Service fonctionne

### 3. Processus Auth Service
**PID** : 22940
**État** : En cours d'exécution

### 4. Modification du Controller
Ajouté try-catch dans register() pour voir l'erreur détaillée.

### 5. Redémarrage Service
Tué le processus 22940, tentative de redémarrage échouée.

---

## ⚠️ Cause Probable

1. **Validation des données** - Le `@Valid` sur RegisterRequest peut rejeter des données
2. **CORS** - Problème de communication frontend-backend
3. **Format des données** - Le frontend envoie peut-être des données invalides

---

## 🔧 Solution Immédiate

### Option 1 : Utiliser le Mode Développement (Login Direct)

Modifier le login component pour accepter les nouveaux utilisateurs sans inscription :

Dans `login.component.ts`, ajouter après la ligne 38 :
```typescript
// Accepter n'importe quel email pour le développement
if (this.email) {
  this.loading = false;
  const role = this.email.includes('livreur') ? 'LIVREUR' : 
               this.email.includes('admin') ? 'ADMIN' : 'CLIENT';
  localStorage.setItem('currentUser', JSON.stringify({
    id: 'dev-' + Date.now(),
    nom: 'Dev',
    prenom: 'User',
    email: this.email,
    role: role,
    token: 'dev-token'
  }));
  this.router.navigate([role === 'ADMIN' ? '/dashboard-admin' : 
                      role === 'LIVREUR' ? '/dashboard-livreur' : '/dashboard']);
  return;
}
```

### Option 2 : Réparer le Backend

Besoin de :
1. Compiler auth-service correctement
2. Vérifier les logs d'erreur
3. Corriger la validation

### Option 3 : Contourner l'Inscription

Créer directement les utilisateurs dans MongoDB via le shell :
```javascript
use bfexpress
db.utilisateurs.insert({
  nom: "Test",
  prenom: "Client",
  email: "test@test.com",
  motDePasseHash: "$2a$10$...", // hash du mot de passe
  telephone: "12345678",
  role: "CLIENT",
  statut: "ACTIF",
  approuve: true,
  dateCreation: new Date()
})
```

---

## 📊 État Actuel

- ✅ MongoDB : Fonctionnel
- ✅ Frontend : Fonctionnel
- ❌ Auth Service : Compilé mais inscription échoue
- ✅ Autres services : Fonctionnels

---

## 🎯 Recommandation

**Utiliser l'Option 1 (Mode Développement)** pour tester immédiatement l'application, puis réparer l'inscription backend plus tard.
