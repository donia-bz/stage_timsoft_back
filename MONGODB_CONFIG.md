# Configuration MongoDB - BFExpress

## 📋 Configuration Actuelle

Tous les services utilisent maintenant **MongoDB Atlas** (base de données cloud) : `bfexpress`

### 🔌 Connexion MongoDB Atlas
- **Cluster** : `timsoftstage.192tpcj.mongodb.net`
- **Base de données** : `bfexpress`
- **Authentification** : Activée (username: donia)
- **Auto-création d'index** : Activée
- **Type** : M0 Sandbox (Gratuit)

---

## 🚀 Ports des Services

| Service | Port | Description |
|---------|------|-------------|
| auth-service | 8082 | Authentification utilisateurs |
| commandes-service | 8081 | Gestion commandes/colis |
| tracking-service | 8083 | Suivi GPS livraisons |
| livreurs-service | 8084 | Gestion livreurs |
| ia-service | 8085 | IA affectation/prédictions |
| vehicles-service | 8086 | Gestion véhicules |
| depots-service | 8087 | Gestion dépôts |
| reclamations-service | 8088 | Gestion réclamations |
| stats-service | 8089 | Évaluations/statistiques |
| audit-service | 8090 | Logs et audit |
| notifications-service | 8091 | Notifications |

---

## 🔧 Vérification MongoDB Atlas

### 1. Vérifier la connexion via MongoDB Compass
1. Ouvrez MongoDB Compass
2. Collez la chaîne de connexion : `mongodb+srv://donia:jCn8Pt1ZhyF5ewRy@timsoftstage.192tpcj.mongodb.net/bfexpress?retryWrites=true&w=majority`
3. Cliquez sur "Connect"
4. Vous devriez voir la base de données `bfexpress`

### 2. Vérifier via MongoDB Atlas Dashboard
1. Connectez-vous à https://cloud.mongodb.com
2. Allez dans votre cluster `timsoftstage`
3. Cliquez sur "Collections" pour voir les collections créées

---

## 📦 Collections MongoDB

Les collections sont créées automatiquement par Spring Data MongoDB :

- `utilisateurs` (auth-service)
- `commandes` (commandes-service)
- `colis` (commandes-service)
- `destinataires` (commandes-service)
- `manifestes` (commandes-service)
- `enlevements` (commandes-service)
- `livreurs` (livreurs-service)
- `vehicules` (livreurs-service/vehicles-service)
- `depots` (livreurs-service/depots-service)
- `affectations_vehicule` (livreurs-service)
- `livraisons` (tracking-service)
- `positions_tracking` (tracking-service)
- `predictions_delai` (ia-service)
- `affectations_ia` (ia-service)
- `reclamations` (reclamations-service)
- `evaluations` (stats-service)
- `audit_logs` (audit-service)
- `notifications` (notifications-service)

---

## 🧪 Test de Connexion

### Test avec Spring Boot
1. Démarrer un service (ex: auth-service)
2. Vérifier les logs :
```
Connected to MongoDB Atlas
Database: bfexpress
Cluster: timsoftstage
```

### Test avec curl
```bash
# Tester auth-service
curl http://localhost:8082/api/auth/users

# Tester vehicles-service
curl http://localhost:8086/api/vehicules
```

---

## ⚠️ Dépannage

### MongoDB ne démarre pas
```bash
# Vérifier si le port est occupé
netstat -an | findstr 27017

# Si occupé, changer le port dans MongoDB
# ou arrêter le processus qui utilise le port
```

### Erreur de connexion Spring Boot
1. Vérifier que MongoDB tourne
2. Vérifier le port 27017
3. Vérifier les logs Spring Boot
4. Redémarrer le service

### Collections non créées
- Spring Data MongoDB crée les collections automatiquement
- Les collections apparaissent lors du premier enregistrement
- Vérifier : `auto-index-creation=true` dans application.properties

---

## 📊 Données de Test

Les données de test sont automatiquement créées par `DataInitializer` (auth-service) :
- Admin : admin@bfexpress.com / admin123
- Client : client@bfexpress.com / client123
- Livreur : livreur@bfexpress.com / livreur123

---

## 🔄 Migration vers MongoDB avec authentification

Si vous souhaitez activer l'authentification MongoDB :

1. Créer un utilisateur dans MongoDB :
```javascript
use admin
db.createUser({
  user: "bfexpress",
  pwd: "votre_mot_de_passe",
  roles: [ { role: "readWrite", db: "bfexpress" } ]
})
```

2. Modifier tous les `application.properties` :
```properties
spring.data.mongodb.uri=mongodb://bfexpress:votre_mot_de_passe@localhost:27017/bfexpress
```

3. Redémarrer tous les services
