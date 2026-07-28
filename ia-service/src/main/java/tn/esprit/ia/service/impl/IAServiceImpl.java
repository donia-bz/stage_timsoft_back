package tn.esprit.ia.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.ia.dto.LivreurDTO;
import tn.esprit.ia.entity.AffectationIA;
import tn.esprit.ia.entity.PredictionDelai;
import tn.esprit.ia.repository.AffectationIARepository;
import tn.esprit.ia.repository.PredictionDelaiRepository;
import tn.esprit.ia.service.IAService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IAServiceImpl implements IAService {

    private final PredictionDelaiRepository predictionDelaiRepository;
    private final AffectationIARepository affectationIARepository;
    private final RestTemplate restTemplate;

    @Value("${ia.python.url:http://localhost:5000/api/ia}")
    private String pythonUrl;

    @Override
    public PredictionDelai predirDelai(String commandeId, Double latDepart, Double longDepart, Double latArrivee, Double longArrivee, String typeService) {
        int delaiPreditMin;
        String versionModele = "v1.0-Haversine-RuleEngine";

        if (latDepart != null && longDepart != null && latArrivee != null && longArrivee != null) {
            double distanceKm = calculateHaversineDistance(latDepart, longDepart, latArrivee, longArrivee);
            // Estimation : Vitesse moyenne 30 km/h + 15 min de temps de prise en charge/embouteillages
            delaiPreditMin = (int) Math.round((distanceKm / 30.0) * 60.0 + 15.0);
            if ("EXPRESS".equalsIgnoreCase(typeService)) {
                delaiPreditMin = Math.max(15, (int) (delaiPreditMin * 0.7)); // 30% plus rapide pour l'express
            }
        } else {
            delaiPreditMin = 45; // Valeur par defaut si coordonnees absentes
        }

        PredictionDelai prediction = PredictionDelai.builder()
                .commandeId(commandeId)
                .delaiPreditMin(delaiPreditMin)
                .dateCalcul(LocalDateTime.now())
                .versionModele(versionModele)
                .build();

        return predictionDelaiRepository.save(prediction);
    }

    @Override
    public AffectationIA calculerAffectation(String colisId, Double latColis, Double longColis, List<LivreurDTO> livreurs) {
        if (livreurs == null || livreurs.isEmpty()) {
            throw new IllegalArgumentException("Aucun livreur disponible pour l'affectation IA");
        }

        // Trouver le meilleur livreur en fonction du score de proximite et de sa note
        LivreurDTO meilleurLivreur = null;
        double meilleurScore = -1.0;

        for (LivreurDTO livreur : livreurs) {
            double distanceKm = (latColis != null && longColis != null && livreur.getLatitudeActuelle() != null && livreur.getLongitudeActuelle() != null)
                    ? calculateHaversineDistance(latColis, longColis, livreur.getLatitudeActuelle(), livreur.getLongitudeActuelle())
                    : 5.0; // 5 km par defaut

            double scoreProximite = 1.0 / (1.0 + distanceKm); // Entre 0 et 1
            double noteNorm = (livreur.getNoteMoyenne() != null ? livreur.getNoteMoyenne() : 5.0) / 5.0;
            double scoreGlobal = (scoreProximite * 0.7) + (noteNorm * 0.3); // 70% distance, 30% note

            if (scoreGlobal > meilleurScore) {
                meilleurScore = scoreGlobal;
                meilleurLivreur = livreur;
            }
        }

        AffectationIA affectation = AffectationIA.builder()
                .colisId(colisId)
                .livreurId(meilleurLivreur.getId())
                .score(Math.round(meilleurScore * 100.0) / 100.0)
                .dateCalcul(LocalDateTime.now())
                .build();

        return affectationIARepository.save(affectation);
    }

    @Override
    public List<PredictionDelai> getPredictionsByCommande(String commandeId) {
        return predictionDelaiRepository.findByCommandeId(commandeId);
    }

    @Override
    public List<AffectationIA> getAffectationsByColis(String colisId) {
        return affectationIARepository.findByColisId(colisId);
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Rayon de la Terre en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
