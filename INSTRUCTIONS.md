# INSTRUCTIONS POUR DÉMARRER LES SERVICES

## PROBLÈME
Maven n'est pas installé sur votre système, donc vous ne pouvez pas utiliser les commandes `mvn`.

## SOLUTION SIMPLE : UTILISER VOTRE IDE

### Option 1 : INTELLIJ IDEA (le plus simple)
1. Ouvrez IntelliJ IDEA
2. Faites "File" → "Open" → Sélectionnez `F:\stage-timsoft-livraison`
3. Attendez que le projet s'importe
4. Cliquez sur chaque fichier suivant et appuyez sur le bouton "Run" (triangle vert) :
   - `auth-service/src/main/java/tn/esprit/auth/AuthServiceApplication.java`
   - `commandes-service/src/main/java/tn/esprit/commandes/CommandesServiceApplication.java`
   - `tracking-service/src/main/java/tn/esprit/tracking/TrackingServiceApplication.java`
   - `livreurs-service/src/main/java/tn/esprit/livreurs/LivreursServiceApplication.java`
   - `ia-service/src/main/java/tn/esprit/ia/IAServiceApplication.java`
   - `notifications-service/src/main/java/tn/esprit/notifications/NotificationsServiceApplication.java`
   - `audit-service/src/main/java/tn/esprit/audit/AuditServiceApplication.java`

### Option 2 : ECLIPSE
1. Ouvrez Eclipse
2. Faites "File" → "Import" → "Maven" → "Existing Maven Projects"
3. Sélectionnez `F:\stage-timsoft-livraison`
4. Cliquez droit sur chaque classe principale → "Run As" → "Java Application"

### Option 3 : VS CODE
1. Ouvrez VS Code
2. Ouvrez le dossier `F:\stage-timsoft-livraison`
3. Installez l'extension "Extension Pack for Java"
4. Cliquez droit sur chaque classe principale → "Run Java"

## IMPORTANT

### MongoDB doit être démarré
- Assurez-vous que MongoDB tourne sur le port 27017
- Sinon les services ne pourront pas se connecter à la base de données

### Ordre de démarrage recommandé
1. Auth Service (port 8082) - le plus important pour la connexion
2. Commandes Service (port 8081)
3. Tracking Service (port 8083)
4. Livreurs Service (port 8084)
5. IA Service (port 8086)
6. Notifications Service (port 8086)
7. Audit Service (port 8087)

### Vérification
Une fois démarrés, vous devriez voir dans les logs :
```
Started AuthServiceApplication in X seconds
Tomcat started on port(s): 8082 (http)
```

## Si vous voulez installer Maven (optionnel)

1. Téléchargez Maven : https://maven.apache.org/download.cgi
2. Extrayez le fichier ZIP (ex: C:\Program Files\Apache\maven)
3. Ajoutez au PATH Windows :
   - Paramètres système → Variables d'environnement
   - Ajoutez `C:\Program Files\Apache\maven\bin` au PATH
4. Redémarrez PowerShell
5. Vérifiez : `mvn --version`

Puis vous pourrez utiliser le script `demarrer-services.bat`