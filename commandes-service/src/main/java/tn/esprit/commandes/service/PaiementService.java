package tn.esprit.commandes.service;

import tn.esprit.commandes.dto.request.PaiementRequest;
import tn.esprit.commandes.dto.response.PaiementResponse;

import tn.esprit.commandes.entity.enums.StatutPaiement;

import java.util.List;

public interface PaiementService {
    PaiementResponse enregistrerPaiement(PaiementRequest request);
    PaiementResponse getPaiementById(String id);
    PaiementResponse getPaiementByCommandeId(String commandeId);
    PaiementResponse updateStatutPaiement(String id, StatutPaiement statut);
    List<PaiementResponse> getAllPaiements();
}
