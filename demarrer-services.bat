@echo off
echo ========================================
echo Demarrage des services Spring Boot
echo ========================================
echo.

REM Vérifier si Java est installé
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java n'est pas installe ou n'est pas dans le PATH
    echo Veuillez installer Java 17 ou superieur
    pause
    exit /b 1
)

echo Java est installe. Demarrage des services...
echo.

REM Démarrer Auth Service (port 8082)
echo [1/7] Demarrage Auth Service (port 8082)...
start "Auth Service" cmd /k "cd /d F:\stage-timsoft-livraison\auth-service && echo Démarrage Auth Service... && echo. && java -jar target\auth-service-0.0.1-SNAPSHOT.jar 2>nul || echo Veuillez d'abord compiler le projet (mvn clean install) && pause"

REM Attendre un peu avant de démarrer le service suivant
timeout /t 5 /nobreak >nul

REM Démarrer Commandes Service (port 8081)
echo [2/7] Demarrage Commandes Service (port 8081)...
start "Commandes Service" cmd /k "cd /d F:\stage-timsoft-livraison\commandes-service && echo Démarrage Commandes Service... && echo. && java -jar target\commandes-service-0.0.1-SNAPSHOT.jar 2>nul || echo Veuillez d'abord compiler le projet (mvn clean install) && pause"

timeout /t 5 /nobreak >nul

REM Démarrer Tracking Service (port 8083)
echo [3/7] Demarrage Tracking Service (port 8083)...
start "Tracking Service" cmd /k "cd /d F:\stage-timsoft-livraison\tracking-service && echo Démarrage Tracking Service... && echo. && java -jar target\tracking-service-0.0.1-SNAPSHOT.jar 2>nul || echo Veuillez d'abord compiler le projet (mvn clean install) && pause"

timeout /t 5 /nobreak >nul

REM Démarrer Livreurs Service (port 8084)
echo [4/7] Demarrage Livreurs Service (port 8084)...
start "Livreurs Service" cmd /k "cd /d F:\stage-timsoft-livraison\livreurs-service && echo Démarrage Livreurs Service... && echo. && java -jar target\livreurs-service-0.0.1-SNAPSHOT.jar 2>nul || echo Veuillez d'abord compiler le projet (mvn clean install) && pause"

timeout /t 5 /nobreak >nul

REM Démarrer IA Service (port 8086)
echo [5/7] Demarrage IA Service (port 8086)...
start "IA Service" cmd /k "cd /d F:\stage-timsoft-livraison\ia-service && echo Démarrage IA Service... && echo. && java -jar target\ia-service-0.0.1-SNAPSHOT.jar 2>nul || echo Veuillez d'abord compiler le projet (mvn clean install) && pause"

timeout /t 5 /nobreak >nul

REM Démarrer Notifications Service (port 8086)
echo [6/7] Demarrage Notifications Service (port 8086)...
start "Notifications Service" cmd /k "cd /d F:\stage-timsoft-livraison\notifications-service && echo Démarrage Notifications Service... && echo. && java -jar target\notifications-service-0.0.1-SNAPSHOT.jar 2>nul || echo Veuillez d'abord compiler le projet (mvn clean install) && pause"

timeout /t 5 /nobreak >nul

REM Démarrer Audit Service (port 8087)
echo [7/7] Demarrage Audit Service (port 8087)...
start "Audit Service" cmd /k "cd /d F:\stage-timsoft-livraison\audit-service && echo Démarrage Audit Service... && echo. && java -jar target\audit-service-0.0.1-SNAPSHOT.jar 2>nul || echo Veuillez d'abord compiler le projet (mvn clean install) && pause"

echo.
echo ========================================
echo Tous les services sont en cours de démarrage
echo ========================================
echo.
echo IMPORTANT: Assurez-vous que MongoDB est démarré sur le port 27017
echo.
echo Les services s'ouvriront dans des fenêtres séparées
echo NE FERMEZ PAS ces fenêtres pendant l'utilisation
echo.
echo Attendez environ 30-60 secondes que les services se chargent
echo Puis testez votre application sur http://localhost:4200
echo.
pause