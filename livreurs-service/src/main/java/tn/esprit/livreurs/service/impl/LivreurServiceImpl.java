package tn.esprit.livreurs.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.livreurs.entity.AffectationVehicule;
import tn.esprit.livreurs.entity.Depot;
import tn.esprit.livreurs.entity.Livreur;
import tn.esprit.livreurs.entity.Vehicule;
import tn.esprit.livreurs.entity.enums.StatutLivreur;
import tn.esprit.livreurs.repository.AffectationVehiculeRepository;
import tn.esprit.livreurs.repository.DepotRepository;
import tn.esprit.livreurs.repository.LivreurRepository;
import tn.esprit.livreurs.client.AuditClient;
import tn.esprit.livreurs.repository.VehiculeRepository;
import tn.esprit.livreurs.service.LivreurService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LivreurServiceImpl implements LivreurService {

    private final LivreurRepository livreurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final DepotRepository depotRepository;
    private final AffectationVehiculeRepository affectationVehiculeRepository;
    private final AuditClient auditClient;

    @Override
    public Livreur saveLivreur(Livreur livreur) {
        if (livreur.getStatut() == null) {
            livreur.setStatut(StatutLivreur.DISPONIBLE);
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
        return livreurRepository.findByStatut(StatutLivreur.DISPONIBLE);
    }

    @Override
    public Livreur updateStatut(String id, StatutLivreur statut) {
        Livreur livreur = getLivreurById(id);
        StatutLivreur ancienStatut = livreur.getStatut();
        livreur.setStatut(statut);
        Livreur saved = livreurRepository.save(livreur);
        auditClient.enregistrerChangementStatut("Livreur", id,
                ancienStatut != null ? ancienStatut.name() : null, statut.name());
        return saved;
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
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule getVehiculeById(String id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicule introuvable avec l'id : " + id));
    }

    @Override
    public Vehicule getVehiculeActifByLivreurId(String livreurId) {
        AffectationVehicule affectation = affectationVehiculeRepository.findByLivreurIdAndDateFinIsNull(livreurId)
                .orElseThrow(() -> new IllegalArgumentException("Aucun véhicule actif pour le livreur : " + livreurId));
        return getVehiculeById(affectation.getVehiculeId());
    }

    @Override
    public AffectationVehicule affecterVehicule(String livreurId, String vehiculeId) {
        getLivreurById(livreurId);
        getVehiculeById(vehiculeId);

        affectationVehiculeRepository.findByLivreurIdAndDateFinIsNull(livreurId)
                .ifPresent(active -> {
                    active.setDateFin(LocalDateTime.now());
                    affectationVehiculeRepository.save(active);
                });

        AffectationVehicule affectation = AffectationVehicule.builder()
                .livreurId(livreurId)
                .vehiculeId(vehiculeId)
                .dateDebut(LocalDateTime.now())
                .build();

        return affectationVehiculeRepository.save(affectation);
    }

    @Override
    public AffectationVehicule desaffecterVehicule(String livreurId) {
        AffectationVehicule active = affectationVehiculeRepository.findByLivreurIdAndDateFinIsNull(livreurId)
                .orElseThrow(() -> new IllegalArgumentException("Aucune affectation active pour le livreur : " + livreurId));
        active.setDateFin(LocalDateTime.now());
        return affectationVehiculeRepository.save(active);
    }

    @Override
    public List<AffectationVehicule> getHistoriqueAffectationsLivreur(String livreurId) {
        return affectationVehiculeRepository.findByLivreurIdOrderByDateDebutDesc(livreurId);
    }

    @Override
    public List<AffectationVehicule> getHistoriqueAffectationsVehicule(String vehiculeId) {
        return affectationVehiculeRepository.findByVehiculeIdOrderByDateDebutDesc(vehiculeId);
    }

    @Override
    public Livreur assignerDepot(String livreurId, String depotId) {
        Livreur livreur = getLivreurById(livreurId);
        getDepotById(depotId);
        livreur.setDepotId(depotId);
        return livreurRepository.save(livreur);
    }

    @Override
    public List<Livreur> getLivreursByDepot(String depotId) {
        getDepotById(depotId);
        return livreurRepository.findByDepotId(depotId);
    }

    @Override
    public Livreur assignerGouvernorat(String livreurId, String gouvernorat) {
        Livreur livreur = getLivreurById(livreurId);
        livreur.setGouvernorat(gouvernorat);
        return livreurRepository.save(livreur);
    }

    @Override
    public List<Livreur> getLivreursByGouvernorat(String gouvernorat) {
        return livreurRepository.findByGouvernorat(gouvernorat);
    }

    @Override
    public List<?> getLivraisonsByLivreur(String livreurId) {
        // Pour l'in, retourne une liste vide - sera implémenté avec le tracking-service
        return List.of();
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
