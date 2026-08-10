package tn.esprit.commandes.service;

import tn.esprit.commandes.dto.response.ColisResponse;
import tn.esprit.commandes.entity.enums.StatutCommande;

import java.util.List;

public interface ColisService {

    List<ColisResponse> getColisByCommande(String commandeId);

    List<ColisResponse> getColisByClient(String clientId);

    ColisResponse getColisById(String id);

    List<ColisResponse> searchColis(String query);

    ColisResponse updateStatut(String id, StatutCommande nouveauStatut);
}
