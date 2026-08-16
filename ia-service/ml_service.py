"""
Service de Machine Learning pour le dispatching des livreurs
Utilise scikit-learn Random Forest pour une meilleure prédiction
"""

import numpy as np
import pandas as pd
from flask import Flask, request, jsonify
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
import joblib
import os
from datetime import datetime
import math

app = Flask(__name__)

# Modèle ML et scaler
model = None
scaler = None
is_trained = False

# Configuration
MODEL_PATH = 'models/rf_dispatch_model.pkl'
SCALER_PATH = 'models/rf_scaler.pkl'
TRAINING_DATA_PATH = 'data/training_data.csv'

class FeaturesExtractor:
    """Extraction de caractéristiques pour le ML"""
    
    @staticmethod
    def calculate_distance(lat1, lon1, lat2, lon2):
        """Calcule la distance Haversine entre deux points GPS"""
        if lat1 is None or lon1 is None or lat2 is None or lon2 is None:
            return 5.0  # Distance par défaut si coordonnées manquantes
        
        R = 6371  # Rayon de la Terre en km
        lat_dist = math.radians(lat2 - lat1)
        lon_dist = math.radians(lon2 - lon1)
        
        a = (math.sin(lat_dist / 2) ** 2 +
             math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) *
             math.sin(lon_dist / 2) ** 2)
        c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
        
        return R * c
    
    @staticmethod
    def extract_features(commande, livreur):
        """Extrait les caractéristiques pour une paire commande-livreur"""
        # Caractéristiques de distance
        lat_cmd = commande.get('latitude') or 36.8
        lon_cmd = commande.get('longitude') or 10.1
        lat_liv = livreur.get('latitudeActuelle') or 36.8
        lon_liv = livreur.get('longitudeActuelle') or 10.1
        
        distance = FeaturesExtractor.calculate_distance(lat_cmd, lon_cmd, lat_liv, lon_liv)
        
        # Caractéristiques de performance
        note_livreur = livreur.get('noteMoyenne') or 5.0
        nb_livraisons = livreur.get('nombreLivraisons') or 0
        
        # Caractéristiques de disponibilité
        est_disponible = 1 if livreur.get('statut') == 'DISPONIBLE' else 0
        est_en_course = 1 if livreur.get('statut') == 'EN_COURSE' else 0
        
        # Caractéristiques géographiques
        meme_gouvernorat = 1 if commande.get('gouvernorat') == livreur.get('gouvernorat') else 0
        
        # Score de proximité (plus c'est proche, mieux c'est)
        score_proximite = 1.0 / (1.0 + distance)
        
        return {
            'distance': distance,
            'score_proximite': score_proximite,
            'note_livreur': note_livreur,
            'nb_livraisons': nb_livraisons,
            'est_disponible': est_disponible,
            'est_en_course': est_en_course,
            'meme_gouvernorat': meme_gouvernorat,
            'capacite_disponible': est_disponible  # Capacité à prendre des commandes
        }

def generate_training_data(n_samples=1000):
    """Génère des données d'entraînement synthétiques"""
    np.random.seed(42)
    
    data = []
    labels = []
    
    for _ in range(n_samples):
        # Simuler une commande
        cmd_lat = 36.8 + np.random.uniform(-0.5, 0.5)
        cmd_lon = 10.1 + np.random.uniform(-0.5, 0.5)
        cmd_gouv = np.random.choice(['Tunis', 'Ariana', 'Sfax', 'Bizerte', 'Monastir'])
        
        # Simuler un livreur
        liv_lat = 36.8 + np.random.uniform(-0.5, 0.5)
        liv_lon = 10.1 + np.random.uniform(-0.5, 0.5)
        liv_gouv = np.random.choice(['Tunis', 'Ariana', 'Sfax', 'Bizerte', 'Monastir'])
        liv_note = np.random.uniform(3.0, 5.0)
        liv_livraisons = np.random.randint(0, 200)
        liv_statut = np.random.choice(['DISPONIBLE', 'EN_COURSE', 'HORS_LIGNE'], p=[0.7, 0.2, 0.1])
        
        # Calculer la distance
        R = 6371
        lat_dist = math.radians(liv_lat - cmd_lat)
        lon_dist = math.radians(liv_lon - cmd_lon)
        a = (math.sin(lat_dist / 2) ** 2 +
             math.cos(math.radians(cmd_lat)) * math.cos(math.radians(liv_lat)) *
             math.sin(lon_dist / 2) ** 2)
        c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
        distance = R * c
        
        # Score de proximité
        score_proximite = 1.0 / (1.0 + distance)
        
        # Étiquette : 1 si bon candidat, 0 sinon
        # Bon candidat : distance < 10km, note > 3.5, disponible
        is_good = (distance < 10.0 and liv_note > 3.5 and liv_statut == 'DISPONIBLE')
        
        data.append({
            'distance': distance,
            'score_proximite': score_proximite,
            'note_livreur': liv_note,
            'nb_livraisons': liv_livraisons,
            'est_disponible': 1 if liv_statut == 'DISPONIBLE' else 0,
            'est_en_course': 1 if liv_statut == 'EN_COURSE' else 0,
            'meme_gouvernorat': 1 if cmd_gouv == liv_gouv else 0
        })
        labels.append(is_good)
    
    return pd.DataFrame(data), np.array(labels)

def train_model():
    """Entraîne le modèle Random Forest"""
    global model, scaler, is_trained
    
    print("🎯 Entraînement du modèle Random Forest...")
    
    # Générer les données d'entraînement
    X, y = generate_training_data(n_samples=2000)
    
    print(f"📊 Données d'entraînement : {X.shape[0]} échantillons, {X.shape[1]} caractéristiques")
    print(f"✅ Positifs : {sum(y)}, Négatifs : {len(y) - sum(y)}")
    
    # Diviser en train/test
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    
    # Normaliser les données
    scaler = StandardScaler()
    X_train_scaled = scaler.fit_transform(X_train)
    X_test_scaled = scaler.transform(X_test)
    
    # Entraîner Random Forest
    model = RandomForestClassifier(
        n_estimators=100,
        max_depth=10,
        min_samples_split=5,
        random_state=42,
        n_jobs=-1
    )
    
    model.fit(X_train_scaled, y_train)
    
    # Évaluer
    y_pred = model.predict(X_test_scaled)
    accuracy = accuracy_score(y_test, y_pred)
    
    print(f"🎯 Précision du modèle : {accuracy:.2%}")
    
    # Importance des caractéristiques
    feature_importance = model.feature_importances_
    print("\n📊 Importance des caractéristiques :")
    for feature, importance in zip(X.columns, feature_importance):
        print(f"  {feature}: {importance:.3f}")
    
    # Sauvegarder le modèle
    os.makedirs('models', exist_ok=True)
    joblib.dump(model, MODEL_PATH)
    joblib.dump(scaler, SCALER_PATH)
    
    is_trained = True
    print(f"✅ Modèle sauvegardé dans {MODEL_PATH}")
    
    return accuracy

def predict_best_driver(commande, livreurs):
    """Prédit le meilleur livreur pour une commande"""
    global model, scaler, is_trained
    
    if not is_trained:
        print("⚠️ Modèle non entraîné, utilisation de la logique simple")
        # Fallback vers la logique simple si modèle non entraîné
        return predict_simple_logic(commande, livreurs)
    
    if not livreurs or len(livreurs) == 0:
        return None
    
    # Préparer les caractéristiques pour chaque livreur
    features_list = []
    livreur_ids = []
    
    for livreur in livreurs:
        features = FeaturesExtractor.extract_features(commande, livreur)
        features_list.append([
            features['distance'],
            features['score_proximite'],
            features['note_livreur'],
            features['nb_livraisons'],
            features['est_disponible'],
            features['est_en_course'],
            features['meme_gouvernorat']
        ])
        livreur_ids.append(livreur.get('id'))
    
    # Normaliser et prédire
    features_array = np.array(features_list)
    features_scaled = scaler.transform(features_array)
    
    # Obtenir les probabilités
    probabilities = model.predict_proba(features_scaled)[:, 1]
    
    # Choisir le livreur avec la plus haute probabilité
    best_idx = np.argmax(probabilities)
    best_livreur_id = livreur_ids[best_idx]
    best_score = probabilities[best_idx]
    
    # Retourner le livreur avec son score
    best_livreur = None
    for livreur in livreurs:
        if livreur.get('id') == best_livreur_id:
            best_livreur = livreur
            break
    
    return {
        'livreur': best_livreur,
        'score': best_score,
        'probabilites': dict(zip(livreur_ids, probabilities.tolist()))
    }

def predict_simple_logic(commande, livreurs):
    """Logique simple de secours (remplace K-Means)"""
    if not livreurs or len(livreurs) == 0:
        return None
    
    # Filtrer uniquement les livreurs du même gouvernorat
    cmd_gouv = commande.get('gouvernorat') or 'Tunis'
    livreurs_meme_gouv = [l for l in livreurs if l.get('gouvernorat') == cmd_gouv]
    
    if not livreurs_meme_gouv:
        print(f"⚠️ Aucun livreur disponible pour le gouvernorat {cmd_gouv}")
        return None
    
    meilleur_livreur = None
    meilleur_score = -1.0
    
    for livreur in livreurs_meme_gouv:
        features = FeaturesExtractor.extract_features(commande, livreur)
        
        # Score simple pondéré avec priorité au même gouvernorat
        score = (features['score_proximite'] * 0.6 + 
                (features['note_livreur'] / 5.0) * 0.3 +
                features['est_disponible'] * 0.1)
        
        if score > meilleur_score:
            meilleur_score = score
            meilleur_livreur = livreur
    
    return {
        'livreur': meilleur_livreur,
        'score': meilleur_score,
        'probabilites': {}
    }

@app.route('/health', methods=['GET'])
def health():
    return jsonify({'status': 'healthy', 'model_trained': is_trained})

@app.route('/train', methods=['POST'])
def train():
    """Endpoint pour entraîner le modèle"""
    try:
        accuracy = train_model()
        return jsonify({
            'status': 'success',
            'accuracy': accuracy,
            'message': 'Modèle entraîné avec succès'
        })
    except Exception as e:
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 500

@app.route('/predict', methods=['POST'])
def predict():
    """Endpoint pour prédire le meilleur livreur"""
    try:
        data = request.json
        commande = data.get('commande')
        livreurs = data.get('livreurs')
        
        if not commande or not livreurs:
            return jsonify({'error': 'Données manquantes'}), 400
        
        result = predict_best_driver(commande, livreurs)
        
        if result:
            return jsonify({
                'livreur_id': result['livreur'].get('id'),
                'score': result['score'],
                'status': 'success'
            })
        else:
            return jsonify({'error': 'Aucun livreur disponible'}), 400
            
    except Exception as e:
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 500

@app.route('/dispatch-global', methods=['POST'])
def dispatch_global():
    """Endpoint pour le dispatching global (remplace K-Means)"""
    try:
        data = request.json
        commandes = data.get('commandes', [])
        livreurs = data.get('livreurs', [])
        
        if not commandes or not livreurs:
            return jsonify({'error': 'Données manquantes'}), 400
        
        print(f"📦 Commandes à dispatcher: {len(commandes)}")
        print(f"👷 Livreurs disponibles: {len(livreurs)}")
        
        # Grouper les commandes et livreurs par gouvernorat
        commandes_par_gouv = {}
        for cmd in commandes:
            gouv = cmd.get('gouvernorat') or 'Tunis'
            if gouv not in commandes_par_gouv:
                commandes_par_gouv[gouv] = []
            commandes_par_gouv[gouv].append(cmd)
        
        livreurs_par_gouv = {}
        for liv in livreurs:
            gouv = liv.get('gouvernorat') or 'Tunis'
            if gouv not in livreurs_par_gouv:
                livreurs_par_gouv[gouv] = []
            livreurs_par_gouv[gouv].append(liv)
        
        print(f"📍 Gouvernorats avec commandes: {list(commandes_par_gouv.keys())}")
        print(f"📍 Gouvernorats avec livreurs: {list(livreurs_par_gouv.keys())}")
        
        # Dispatching par gouvernorat
        affectations = {}
        commande_ids = []
        max_commandes_par_livreur = 5  # Limite de commandes par livreur
        
        for gouv, cmds_gouv in commandes_par_gouv.items():
            livreurs_gouv = livreurs_par_gouv.get(gouv, [])
            
            print(f"\n🏛️ Gouvernorat {gouv}: {len(cmds_gouv)} commandes, {len(livreurs_gouv)} livreurs")
            
            if not livreurs_gouv:
                print(f"⚠️ Aucun livreur disponible pour {gouv}")
                continue
            
            # Calculer la capacité totale des livreurs
            capacite_totale = len(livreurs_gouv) * max_commandes_par_livreur
            commandes_a_affecter = min(len(cmds_gouv), capacite_totale)
            
            print(f"📊 Capacité totale: {capacite_totale} commandes")
            print(f"📦 Commandes à affecter: {commandes_a_affecter}")
            
            if commandes_a_affecter == 0:
                print(f"⚠️ Pas assez de livreurs pour toutes les commandes")
                continue
            
            # Utiliser Random Forest pour assigner les commandes aux livreurs du même gouvernorat
            for i, commande in enumerate(cmds_gouv[:commandes_a_affecter]):
                result = predict_best_driver(commande, livreurs_gouv)
                if result and result['livreur']:
                    livreur_id = result['livreur'].get('id')
                    
                    # Vérifier si le livreur a atteint sa limite
                    commandes_actuelles = len(affectations.get(livreur_id, []))
                    if commandes_actuelles >= max_commandes_par_livreur:
                        print(f"⚠️ Livreur {livreur_id} a atteint sa limite ({max_commandes_par_livreur} commandes)")
                        # Essayer de trouver un autre livreur disponible
                        for autre_livreur in livreurs_gouv:
                            autre_id = autre_livreur.get('id')
                            if autre_id != livreur_id and len(affectations.get(autre_id, [])) < max_commandes_par_livreur:
                                livreur_id = autre_id
                                print(f"🔄 Redirection vers livreur {livreur_id}")
                                break
                    
                    if livreur_id not in affectations:
                        affectations[livreur_id] = []
                    affectations[livreur_id].append(commande.get('id'))
                    commande_ids.append(commande.get('id'))
                    
                    print(f"✅ Commande {commande.get('id')} -> Livreur {livreur_id} (score: {result['score']:.2f})")
            
            # Prévenir si certaines commandes ne sont pas affectées
            if len(cmds_gouv) > commandes_a_affecter:
                print(f"⚠️ {len(cmds_gouv) - commandes_a_affecter} commandes non affectées (pas assez de livreurs)")
        
        print(f"\n🎯 Total affectations: {len(commande_ids)} commandes à {len(affectations)} livreurs")
        
        return jsonify({
            'affectations': affectations,
            'total_affectations': len(commande_ids),
            'status': 'success'
        })
        
    except Exception as e:
        print(f"❌ Erreur dispatching: {str(e)}")
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 500

if __name__ == '__main__':
    print("🚀 Démarrage du service ML pour le dispatching...")
    print("📍 Flask application running on http://localhost:5000")
    
    # Entraîner le modèle au démarrage si non entraîné
    if not is_trained:
        print("🎯 Entraînement initial du modèle...")
        train_model()
    
    app.run(host='0.0.0.0', port=5000, debug=True)
