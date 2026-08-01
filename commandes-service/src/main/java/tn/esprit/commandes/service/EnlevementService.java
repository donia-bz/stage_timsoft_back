package tn.esprit.commandes.service;

import tn.esprit.commandes.entity.Enlevement;

import java.util.List;

public interface EnlevementService {

    Enlevement createEnlevement(Enlevement enlevement);

    Enlevement getEnlevementById(String id);

    List<Enlevement> getEnlevementsByClient(String clientId);

    List<Enlevement> getEnlevementsByLivreur(String livreurId);

    List<Enlevement> getAllEnlevements();

    Enlevement updateEnlevement(String id, Enlevement enlevement);

    void deleteEnlevement(String id);
}
