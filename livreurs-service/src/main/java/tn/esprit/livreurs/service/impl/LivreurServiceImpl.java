package tn.esprit.livreurs.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.livreurs.entity.Depot;
import tn.esprit.livreurs.entity.Livreur;
import tn.esprit.livreurs.entity.Vehicule;
import tn.esprit.livreurs.repository.DepotRepository;
import tn.esprit.livreurs.repository.LivreurRepository;
import tn.esprit.livreurs.repository.VehiculeRepository;
import tn.esprit.livreurs.service.LivreurService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivreurServiceImpl implements LivreurService {

    private final LivreurRepository livreurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final DepotRepository depotRepository;

    @Override
    public Livreur saveLivreur(Livreur livreur) {
        if (livreur.getStatut() == null) {
            livreur.setStatut("disponible");
        }
        return livreurRepository.save(livreur);
    }

    @Override
    public Livreur getLivreurById(String id) {
        return livreurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livreur introuvable avec l'id : " + id));
    }

    @Override
    public List<Livreur> getAllLivreurs() {
        return livreurRepository.findAll();
    }

    @Override
    public List<Livreur> getLivreursDisponibles() {
        return livreurRepository.findByStatut("disponible");
    }

    @Override
    public Livreur updateStatut(String id, String statut) {
        Livreur livreur = getLivreurById(id);
        livreur.setStatut(statut);
        return livreurRepository.save(livreur);
    }

    @Override
    public Livreur updatePosition(String id, Double latitude, Double longitude) {
        Livreur livreur = getLivreurById(id);
        livreur.setLatitudeActuelle(latitude);
        livreur.setLongitudeActuelle(longitude);
        return livreurRepository.save(livreur);
    }

    @Override
    public Vehicule saveVehicule(Vehicule vehicule) {
        Vehicule saved = vehiculeRepository.save(vehicule);
        if (vehicule.getLivreurId() != null) {
            Livreur livreur = getLivreurById(vehicule.getLivreurId());
            livreur.setVehiculeId(saved.getId());
            livreurRepository.save(livreur);
        }
        return saved;
    }

    @Override
    public Vehicule getVehiculeById(String id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicule introuvable avec l'id : " + id));
    }

    @Override
    public Vehicule getVehiculeByLivreurId(String livreurId) {
        return vehiculeRepository.findByLivreurId(livreurId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicule introuvable pour le livreur : " + livreurId));
    }

    @Override
    public Depot saveDepot(Depot depot) {
        return depotRepository.save(depot);
    }

    @Override
    public Depot getDepotById(String id) {
        return depotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Depot introuvable avec l'id : " + id));
    }

    @Override
    public List<Depot> getAllDepots() {
        return depotRepository.findAll();
    }
}
