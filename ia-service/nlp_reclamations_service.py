import uvicorn
from fastapi import FastAPI, Request
from pydantic import BaseModel
from typing import Optional, Dict, Any, List
from fastapi.middleware.cors import CORSMiddleware
from textblob import TextBlob
from textblob_fr import PatternTagger, PatternAnalyzer
import datetime
import random

app = FastAPI(title="BFExpress IA Reclamations API", version="1.0.0")

# Configuration CORS pour permettre au frontend Angular de s'y connecter
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # À restreindre en production
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# --- Modèles de données ---
class ReclamationAnalyseRequest(BaseModel):
    reclamation_id: str
    description: str
    type_reclamation: Optional[str] = None
    client_id: Optional[str] = None
    commande_id: Optional[str] = None
    date_creation: Optional[str] = None

class ReponseGenererRequest(BaseModel):
    reclamation_id: str
    type_reponse: str  # 'FORMELLE', 'EMPATHIQUE', 'TECHNIQUE'
    contexte: Optional[Dict[str, Any]] = None

class AnomaliesDetectRequest(BaseModel):
    reclamations: List[Dict[str, Any]]
    days: Optional[int] = 7

# --- Helpers NLP ---
def get_sentiment(text: str):
    """Calcule la polarité du texte en français"""
    # Utilisation de textblob-fr
    blob = TextBlob(text, pos_tagger=PatternTagger(), analyzer=PatternAnalyzer())
    polarity = blob.sentiment[0] # de -1.0 (très négatif) à 1.0 (très positif)
    
    if polarity <= -0.2:
        return 'NEGATIF', polarity
    elif polarity >= 0.2:
        return 'POSITIF', polarity
    else:
        return 'NEUTRE', polarity

def detect_problem(text: str, req_type: str):
    """Détecte le problème principal à partir des mots-clés"""
    text = text.lower()
    req_type = (req_type or "").lower()
    
    if "retard" in text or "retard" in req_type or "pas encore" in text:
        return "Retard de livraison"
    elif "cassé" in text or "endommagé" in text or "abimé" in text:
        return "Colis endommagé"
    elif "perdu" in text or "introuvable" in text:
        return "Colis perdu"
    elif "comportement" in text or "impoli" in text or "livreur" in text:
        return "Problème avec le livreur"
    elif "paiement" in text or "remboursement" in text or "argent" in text:
        return "Problème financier"
    else:
        return "Problème standard"

def get_suggestion_and_time(probleme: str):
    """Renvoie une suggestion d'action et un temps de résolution estimé"""
    if probleme == "Retard de livraison":
        return "Vérifier statut GPS et relancer le livreur", 8
    elif probleme == "Colis endommagé":
        return "Demander des photos et proposer un dédommagement", 24
    elif probleme == "Colis perdu":
        return "Lancer enquête au dépôt", 48
    elif probleme == "Problème avec le livreur":
        return "Contacter le livreur et appliquer un avertissement si nécessaire", 12
    elif probleme == "Problème financier":
        return "Vérifier l'historique des transactions et régulariser", 24
    else:
        return "Réponse standard", 24

# --- Endpoints ---

@app.post("/api/ia/analyser-reclamation")
async def analyser_reclamation(req: ReclamationAnalyseRequest):
    print(f"🔍 Analyse de la réclamation {req.reclamation_id}...")
    
    # 1. Analyse de sentiment
    sentiment, polarity = get_sentiment(req.description)
    
    # 2. Détection du problème
    probleme = detect_problem(req.description, req.type_reclamation)
    
    # 3. Suggestions et temps estimé
    suggestion, temps_estime = get_suggestion_and_time(probleme)
    
    # 4. Ajustement de la priorité selon le sentiment et le temps estimé
    if polarity <= -0.5 or temps_estime <= 12:
        priorite = "URGENT"
    elif polarity >= 0.2:
        priorite = "FAIBLE"
    else:
        priorite = "NORMAL"
        
    confidence = round(0.7 + abs(polarity) * 0.2, 2)  # Score de confiance de l'IA (0.7 à 0.9)

    return {
        "priorite": priorite,
        "sentiment": sentiment,
        "problemeDetecte": probleme,
        "suggestionAction": suggestion,
        "tempsResolutionEstime": temps_estime,
        "confidence": confidence,
        "polarityScore": round(polarity, 2)
    }

@app.post("/api/ia/generer-reponse")
async def generer_reponse(req: ReponseGenererRequest):
    print(f"✍️ Génération de réponse {req.type_reponse} pour la réclamation {req.reclamation_id}")
    ctx = req.contexte or {}
    
    client_name = ctx.get("client_name", "Cher client")
    ref_commande = ctx.get("ref_commande", "votre commande")
    probleme = ctx.get("probleme", "le problème signalé")
    
    # Modèles de réponses basés sur le type
    if req.type_reponse == "EMPATHIQUE":
        templates = [
            f"Bonjour {client_name},\n\nJe suis sincèrement désolé(e) d'apprendre que vous avez rencontré un problème concernant {ref_commande}. Nous comprenons tout à fait votre frustration face à cette situation ({probleme.lower()}).\n\nSachez que nous avons immédiatement pris en charge votre dossier. Notre équipe met tout en œuvre pour trouver une solution dans les plus brefs délais. Nous vous tiendrons informé(e) très rapidement.\n\nMerci de votre patience et de votre compréhension.\n\nCordialement,\nL'équipe Support BFExpress",
            f"Cher(e) {client_name},\n\nNous vous présentons toutes nos excuses pour la gêne occasionnée au sujet de {ref_commande}. Vous avez tout à fait raison de nous signaler {probleme.lower()}.\n\nVotre satisfaction est notre priorité, c'est pourquoi j'ai personnellement remonté votre réclamation à notre service qualité. Nous reviendrons vers vous sous peu avec une solution concrète.\n\nBien à vous,\nLe service client BFExpress"
        ]
    elif req.type_reponse == "FORMELLE":
        templates = [
            f"Bonjour {client_name},\n\nNous accusons réception de votre réclamation concernant la commande {ref_commande} au sujet de : {probleme}.\n\nVotre dossier a été transmis au service compétent pour analyse. Une enquête interne a été ouverte afin d'identifier la cause du dysfonctionnement. Nous reviendrons vers vous avec des éléments de réponse dans les délais impartis.\n\nCordialement,\nService des Réclamations BFExpress",
            f"Madame, Monsieur {client_name},\n\nSuite à votre retour concernant {ref_commande}, nous avons bien noté le motif de votre requête ({probleme}).\n\nNos équipes procèdent actuellement aux vérifications nécessaires. Nous vous contacterons dès que nous aurons de plus amples informations.\n\nSalutations distinguées,\nSupport Client BFExpress"
        ]
    elif req.type_reponse == "TECHNIQUE":
        templates = [
            f"Bonjour {client_name},\n\nSuite à l'analyse de votre dossier (Réf: {ref_commande}) lié à l'incident de type '{probleme}', nos journaux logistiques indiquent qu'une vérification de traçabilité est requise.\n\nLe ticket a été escaladé au niveau 2 de notre support. Les données de dispatching et de localisation sont en cours de rapprochement. Vous serez notifié(e) automatiquement lors du changement de statut de votre ticket.\n\nL'équipe Technique BFExpress",
        ]
    else:
        templates = [f"Bonjour {client_name},\n\nNous avons bien reçu votre réclamation pour {ref_commande}. Nous la traitons actuellement.\n\nCordialement,"]

    # Choisir un template au hasard
    texte = random.choice(templates)
    
    return {
        "texte": texte,
        "confidence": round(random.uniform(0.85, 0.95), 2)
    }

@app.post("/api/detect-anomalies")
async def detect_anomalies(req: AnomaliesDetectRequest):
    # Implémentation basique pour l'API (reprend la logique de détection)
    print("🔍 Détection d'anomalies en cours...")
    
    anomalies = []
    total = len(req.reclamations)
    
    if total > 0:
        # On simule la détection d'une anomalie IA si beaucoup de réclamations urgentes
        urgentes = sum(1 for r in req.reclamations if (r.get("statut") == "EN_ATTENTE" and get_sentiment(r.get("description", ""))[0] == "NEGATIF"))
        
        if urgentes > 2:
            anomalies.append({
                "type": "ALERTE_IA_SENTIMENT",
                "niveau": "HAUT",
                "description": f"L'IA a détecté {urgentes} réclamations en attente avec un ton très négatif.",
                "actionRecommandee": "Contacter ces clients immédiatement avec une approche empathique."
            })
            
    return {
        "anomalies": anomalies,
        "date_analyse": datetime.datetime.now().isoformat()
    }

if __name__ == "__main__":
    print("🚀 Démarrage du microservice IA NLP pour les réclamations sur le port 8001...")
    uvicorn.run("nlp_reclamations_service:app", host="0.0.0.0", port=8001, reload=True)
