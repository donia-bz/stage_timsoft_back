package tn.esprit.tracking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tracking.entity.Livraison;
import tn.esprit.tracking.entity.PositionTracking;
import tn.esprit.tracking.entity.enums.StatutLivraison;
import tn.esprit.tracking.repository.LivraisonRepository;
import tn.esprit.tracking.repository.PositionTrackingRepository;
import tn.esprit.tracking.service.TrackingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

    private final LivraisonRepository livraisonRepository;
    private final PositionTrackingRepository positionTrackingRepository;

    @Override
    public Livraison creerLivraison(String colisId, String livreurId) {
        Livraison livraison = Livraison.builder()
                .colisId(colisId)
                .livreurId(livreurId)
                .statut(StatutLivraison.AFFECTEE)
                .dateAffectation(LocalDateTime.now())
                .distanceKm(0.0)
                .build();
        return livraisonRepository.save(livraison);
    }

    @Override
    public Livraison demarrerLivraison(String id) {
        Livraison livraison = getLivraisonById(id);
        livraison.setStatut(StatutLivraison.EN_COURS);
        livraison.setDateDebut(LocalDateTime.now());
        return livraisonRepository.save(livraison);
    }

    @Override
    public Livraison terminerLivraison(String id) {
        Livraison livraison = getLivraisonById(id);
        livraison.setStatut(StatutLivraison.LIVREE);
        livraison.setDateFin(LocalDateTime.now());
        return livraisonRepository.save(livraison);
    }

    @Override
    public Livraison echouerLivraison(String id) {
        Livraison livraison = getLivraisonById(id);
        livraison.setStatut(StatutLivraison.ECHOUEE);
        livraison.setDateFin(LocalDateTime.now());
        return livraisonRepository.save(livraison);
    }

    @Override
    public PositionTracking ajouterPosition(String livraisonId, Double latitude, Double longitude) {
        getLivraisonById(livraisonId);

        PositionTracking tracking = PositionTracking.builder()
                .livraisonId(livraisonId)
                .latitude(latitude)
                .longitude(longitude)
                .horodatage(LocalDateTime.now())
                .build();

        return positionTrackingRepository.save(tracking);
    }

    @Override
    public List<PositionTracking> getPositions(String livraisonId) {
        return positionTrackingRepository.findByLivraisonIdOrderByHorodatageAsc(livraisonId);
    }

    @Override
    public Livraison getLivraisonById(String id) {
        return livraisonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livraison introuvable avec l'id : " + id));
    }

    @Override
    public List<Livraison> getLivraisonsByLivreur(String livreurId) {
        return livraisonRepository.findByLivreurId(livreurId);
    }

    @Override
    public List<Livraison> getLivraisonsByColis(String colisId) {
        return livraisonRepository.findByColisId(colisId);
    }

    @Override
    public List<Livraison> getAllLivraisons() {
        return livraisonRepository.findAll();
    }
}
