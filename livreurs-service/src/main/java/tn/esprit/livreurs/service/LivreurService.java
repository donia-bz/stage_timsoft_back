package tn.esprit.livreurs.service;

import tn.esprit.livreurs.entity.Depot;
import tn.esprit.livreurs.entity.Livreur;
import tn.esprit.livreurs.entity.Vehicule;

import java.util.List;

public interface LivreurService {
    Livreur saveLivreur(Livreur livreur);
    Livreur getLivreurById(String id);
    List<Livreur> getAllLivreurs();
    List<Livreur> getLivreursDisponibles();
    Livreur updateStatut(String id, String statut);
    Livreur updatePosition(String id, Double latitude, Double longitude);

    // Vehicule
    Vehicule saveVehicule(Vehicule vehicule);
    Vehicule getVehiculeById(String id);
    Vehicule getVehiculeByLivreurId(String livreurId);

    // Depot
    Depot saveDepot(Depot depot);
    Depot getDepotById(String id);
    List<Depot> getAllDepots();
}
