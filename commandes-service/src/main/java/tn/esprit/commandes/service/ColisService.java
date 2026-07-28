package tn.esprit.commandes.service;

import tn.esprit.commandes.dto.response.ColisResponse;

import java.util.List;

public interface ColisService {

    List<ColisResponse> getColisByCommande(String commandeId);

    ColisResponse getColisById(String id);
}
