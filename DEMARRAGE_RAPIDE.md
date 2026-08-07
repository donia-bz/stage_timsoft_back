# 🚀 Guide de Démarrage Rapide - BFExpress

## ⚠️ Résolution du problème d'inscription

L'erreur "Une erreur est survenue lors de l'inscription" est causée par le fait que le **service auth-service n'est pas démarré**.

---

## 🔧 Solution Immédiate

### 1. Démarrer MongoDB (vérifier qu'il tourne)
```bash
# Vérifier si MongoDB est actif
netstat -an | findstr 27017
```

### 2. Démarrer le service auth-service
```bash
cd F:\stage-timsoft-livraison\auth-service
mvn spring-boot:run
```

### 3. Démarrer les services essentiels (optionnel mais recommandé)
```bash
# Terminal 1 - Auth Service
cd F:\stage-timsoft-livraison\auth-service
mvn spring-boot:run

# Terminal 2 - Vehicles Service  
cd F:\stage-timsoft-livraison\vehicles-service
mvn spring-boot:run

# Terminal 3 - Depots Service
cd F:\stage-timsoft-livraison\depots-service
mvn spring-boot:run

# Terminal 4 - Livreurs Service
cd F:\stage-timsoft-livraison\livreurs-service
mvn spring-boot:run
```

### 4. Démarrer le frontend
```bash
cd F:\stage_timsoft_frontend\BFExpress
ng serve
```

---

## 🧪 Test de Connexion

Une fois auth-service démarré, testez avec :
```bash
curl http://localhost:8082/api/auth/users
```

Vous devriez voir les utilisateurs de test :
- Admin : admin@bfexpress.com
- Client : client@bfexpress.com  
- Livreur : livreur@bfexpress.com

---

## 📱 Test de l'Inscription

### Devenir Client
1. Accédez à http://localhost:4200
2. Cliquez sur "Devenir Client"
3. Remplissez le formulaire
4. L'inscription devrait réussir

### Devenir Livreur
1. Accédez à http://localhost:4200
2. Cliquez sur "Devenir Livreur"
3. Remplissez le formulaire complet
4. L'inscription devrait réussir

---

## 🔍 Dépannage

### L'inscription échoue toujours ?

1. **Vérifier que auth-service tourne** :
   ```bash
   curl http://localhost:8082/api/auth/users
   ```

2. **Vérifier les logs du service** :
   - Regardez dans le terminal où vous avez lancé `mvn spring-boot:run`
   - Cherchez les erreurs

3. **Vérifier MongoDB** :
   ```bash
   mongo
   use bfexpress
   show collections
   ```

4. **Redémarrer le service** :
   ```bash
   # Arrêter avec Ctrl+C
   # Relancer
   mvn spring-boot:run
   ```

### Erreur "Identifiants incorrects" ?

C'est normal pour les nouveaux comptes ! Le workflow est :
1. **Inscription** → Statut "INSCRIPTION"
2. **Admin approuve** → Statut "ACTIF"
3. **Connexion** possible

Pour tester en mode développement, utilisez les comptes de test :
- Admin : admin@bfexpress.com / admin123
- Client : client@bfexpress.com / client123
- Livreur : livreur@bfexpress.com / livreur123

---

## 📋 Comptes de Test (Mode Développement)

Les comptes sont automatiquement créés par `DataInitializer` lors du démarrage d'auth-service :

| Rôle | Email | Mot de passe | Accès |
|------|-------|--------------|-------|
| Admin | admin@bfexpress.com | admin123 | Dashboard Admin |
| Client | client@bfexpress.com | client123 | Dashboard Client |
| Livreur | livreur@bfexpress.com | livreur123 | Dashboard Livreur |

---

## 🎯 Ports des Services

| Service | Port | État |
|---------|------|------|
| auth-service | 8082 | **OBLIGATOIRE** pour inscription |
| vehicles-service | 8086 | Pour gestion véhicules |
| depots-service | 8087 | Pour gestion dépôts |
| livreurs-service | 8084 | Pour gestion livreurs |
| commandes-service | 8081 | Pour commandes |
| tracking-service | 8083 | Pour tracking GPS |
| ia-service | 8085 | Pour IA |
| reclamations-service | 8088 | Pour réclamations |
| stats-service | 8089 | Pour évaluations |
| audit-service | 8090 | Pour logs |
| notifications-service | 8091 | Pour notifications |

---

## 💡 Commandes Utiles

### Compiler tous les services
```bash
cd F:\stage-timsoft-livraison
mvn clean install
```

### Vérifier les ports utilisés
```bash
netstat -an | findstr 808
```

### Arrêter un service
Dans le terminal du service : `Ctrl+C`

### Vider la base MongoDB
```bash
mongo
use bfexpress
db.dropDatabase()
```

---

## 📞 Problèmes Persistants ?

Si après avoir démarré auth-service l'inscription échoue toujours :

1. Vérifiez les logs du service pour les erreurs
2. Vérifiez que MongoDB est accessible
3. Vérifiez qu'il n'y a pas de conflit de ports
4. Essayez de recréer la base MongoDB

---

## ✅ Checklist Avant de Tester

- [ ] MongoDB est démarré et accessible
- [ ] auth-service est démarré (port 8082)
- [ ] Frontend est démarré (port 4200)
- [ ] Testé `curl http://localhost:8082/api/auth/users`
- [ ] Essayé inscription avec compte de test

Une fois tout validé, l'inscription devrait fonctionner ! 🎉
