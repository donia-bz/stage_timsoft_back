package tn.esprit.tracking.service;

import tn.esprit.tracking.entity.Livraison;
import tn.esprit.tracking.entity.PositionTracking;

import java.util.List;

public interface TrackingService {
    Livraison creerLivraison(String commandeId, String livreurId);
    Livraison demarrerLivraison(String id);
    Livraison terminerLivraison(String id);
    Livraison echouerLivraison(String id);
    PositionTracking ajouterPosition(String livraisonId, Double latitude, Double longitude);
    List<PositionTracking> getPositions(String livraisonId);
    Livraison getLivraisonById(String id);
    List<Livraison> getLivraisonsByLivreur(String livreurId);
    List<Livraison> getLivraisonsByCommande(String commandeId);
    List<Livraison> getAllLivraisons();
}
