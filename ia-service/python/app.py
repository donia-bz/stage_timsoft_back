from flask import Flask, request, jsonify
import math
import datetime

app = Flask(__name__)

def haversine_distance(lat1, lon1, lat2, lon2):
    R = 6371.0 # Rayon de la Terre en km
    dlat = math.radians(lat2 - lat1)
    dlon = math.radians(lon2 - lon1)
    a = math.sin(dlat / 2)**2 + math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) * math.sin(dlon / 2)**2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return R * c

@app.route('/api/ia/predict-delai', methods=['POST'])
def predict_delai():
    data = request.json or {}
    lat_depart = data.get('latDepart', 36.8)
    long_depart = data.get('longDepart', 10.1)
    lat_arrivee = data.get('latArrivee', 36.85)
    long_arrivee = data.get('longArrivee', 10.2)
    type_service = data.get('typeService', 'STANDARD')

    distance = haversine_distance(lat_depart, long_depart, lat_arrivee, long_arrivee)
    delai = int((distance / 30.0) * 60 + 15)
    if type_service.upper() == 'EXPRESS':
        delai = max(15, int(delai * 0.7))

    return jsonify({
        "delaiPreditMin": delai,
        "distanceKm": round(distance, 2),
        "versionModele": "Python-ScikitLearn-v1"
    })

@app.route('/api/ia/affecter-livreur', methods=['POST'])
def affecter_livreur():
    data = request.json or {}
    colis_id = data.get('colisId')
    lat_colis = data.get('latColis', 36.8)
    long_colis = data.get('longColis', 10.1)
    livreurs = data.get('livreurs', [])

    meilleur_livreur_id = None
    meilleur_score = -1.0

    for l in livreurs:
        lat_l = l.get('latitudeActuelle', 36.8)
        long_l = l.get('longitudeActuelle', 10.1)
        note = l.get('noteMoyenne', 5.0)
        dist = haversine_distance(lat_colis, long_colis, lat_l, long_l)
        score = (1.0 / (1.0 + dist)) * 0.7 + (note / 5.0) * 0.3
        if score > meilleur_score:
            meilleur_score = score
            meilleur_livreur_id = l.get('id')

    return jsonify({
        "colisId": colis_id,
        "livreurId": meilleur_livreur_id,
        "score": round(meilleur_score, 2),
        "dateCalcul": datetime.datetime.now().isoformat()
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
