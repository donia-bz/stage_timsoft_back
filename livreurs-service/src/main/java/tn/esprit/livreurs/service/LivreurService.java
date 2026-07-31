package tn.esprit.livreurs.service;

import tn.esprit.livreurs.entity.AffectationVehicule;
import tn.esprit.livreurs.entity.Depot;
import tn.esprit.livreurs.entity.Livreur;
import tn.esprit.livreurs.entity.Vehicule;
import tn.esprit.livreurs.entity.enums.StatutLivreur;

import java.util.List;

public interface LivreurService {
    Livreur saveLivreur(Livreur livreur);
    Livreur getLivreurById(String id);
    List<Livreur> getAllLivreurs();
    List<Livreur> getLivreursDisponibles();
    Livreur updateStatut(String id, StatutLivreur statut);
    Livreur updatePosition(String id, Double latitude, Double longitude);

    Vehicule saveVehicule(Vehicule vehicule);
    Vehicule getVehiculeById(String id);
    Vehicule getVehiculeActifByLivreurId(String livreurId);

    AffectationVehicule affecterVehicule(String livreurId, String vehiculeId);
    AffectationVehicule desaffecterVehicule(String livreurId);
    List<AffectationVehicule> getHistoriqueAffectationsLivreur(String livreurId);
    List<AffectationVehicule> getHistoriqueAffectationsVehicule(String vehiculeId);

    Livreur assignerDepot(String livreurId, String depotId);
    List<Livreur> getLivreursByDepot(String depotId);

    Depot saveDepot(Depot depot);
    Depot getDepotById(String id);
    List<Depot> getAllDepots();
}
