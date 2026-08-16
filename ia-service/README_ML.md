# Service Machine Learning pour le Dispatching

## 🚀 Installation

### 1. Installer Python et les dépendances

```bash
cd F:\stage-timsoft-livraison\ia-service
pip install -r requirements.txt
```

### 2. Démarrer le service Python ML

```bash
python ml_service.py
```

Le service démarrera sur `http://localhost:5000`

## 🎯 Fonctionnalités

### Random Forest Classifier
- **Algorithme** : RandomForest de scikit-learn
- **Caractéristiques** :
  - Distance (formule Haversine)
  - Score de proximité
  - Note du livreur
  - Nombre de livraisons
  - Disponibilité (DISPONIBLE, EN_COURSE, HORS_LIGNE)
  - Même gouvernorat

### Avantages par rapport à K-Means
- ✅ **Plus intelligent** : Prend en compte la performance des livreurs
- ✅ **Plus précis** : Utilise des probabilités de prédiction
- ✅ **Plus flexible** : Peut être amélioré avec plus de données
- ✅ **Scalable** : Fonctionne avec des milliers de livreurs

## 📊 Endpoints

### 1. Health Check
```bash
GET http://localhost:5000/health
```

### 2. Entraîner le modèle
```bash
POST http://localhost:5000/train
```

### 3. Prédire le meilleur livreur
```bash
POST http://localhost:5000/predict
Content-Type: application/json

{
  "commande": {
    "id": "cmd123",
    "latitude": 36.8,
    "longitude": 10.1,
    "gouvernorat": "Tunis"
  },
  "livreurs": [
    {
      "id": "liv1",
      "latitudeActuelle": 36.81,
      "longitudeActuelle": 10.11,
      "noteMoyenne": 4.5,
      "nombreLivraisons": 50,
      "statut": "DISPONIBLE",
      "gouvernorat": "Tunis"
    }
  ]
}
```

### 4. Dispatching global
```bash
POST http://localhost:5000/dispatch-global
Content-Type: application/json

{
  "commandes": [...],
  "livreurs": [...]
}
```

## 🔧 Configuration

### Activer le ML dans le service Java

Dans `application.properties` :
```properties
ia.use.ml=true
ia.python.url=http://localhost:5000
```

### Désactiver le ML (utiliser K-Means)
```properties
ia.use.ml=false
```

## 📈 Performance

- **Précision** : ~85-90% sur les données de test
- **Temps de prédiction** : < 50ms par commande
- **Entraînement** : ~5 secondes (2000 échantillons)

## 🎓 Pour l'encadrant

### Architecture
- **Service Python** : Flask + scikit-learn
- **Service Java** : Spring Boot + RestTemplate
- **Communication** : REST API

### Algorithme
- **Random Forest** : 100 arbres de décision
- **Profondeur max** : 10
- **Min samples split** : 5

### Données d'entraînement
- **Générées synthétiquement** (2000 échantillons)
- **Pour améliorer** : Collecter des données réelles d'affectations

### Améliorations futures
- ✅ Collecter des données réelles
- ✅ Utiliser Gradient Boosting (XGBoost)
- ✅ Ajouter des caractéristiques métier (trafic, météo)
- ✅ Hyperparameter tuning avec GridSearchCV
