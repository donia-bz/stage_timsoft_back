@echo off
echo ===================================================
echo     Demarrage du Service IA NLP (Reclamations)
echo ===================================================
echo.
echo Ce service tourne sur FastAPI (Port 8001)
echo.

cd /d "%~dp0"
call uvicorn nlp_reclamations_service:app --host 0.0.0.0 --port 8001 --reload

pause
