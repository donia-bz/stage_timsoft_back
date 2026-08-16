@echo off
echo ========================================
echo Service Machine Learning - Dispatching
echo ========================================
echo.
echo Installation des dependances Python...
pip install -r requirements.txt
echo.
echo Demarrage du service ML sur http://localhost:5000
echo.
python ml_service.py
pause
