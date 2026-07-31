package tn.esprit.commandes.service;

import tn.esprit.commandes.dto.request.CommandeRequest;
import tn.esprit.commandes.dto.response.CommandeResponse;
import tn.esprit.commandes.entity.enums.StatutCommande;

import java.util.List;

public interface CommandeService {

    CommandeResponse creerCommande(CommandeRequest request);

    CommandeResponse getCommandeById(String id);

    List<CommandeResponse> getAllCommandes();

    List<CommandeResponse> getCommandesByClient(String clientId);

    CommandeResponse updateStatut(String id, StatutCommande nouveauStatut);

    void supprimerCommande(String id);
}
