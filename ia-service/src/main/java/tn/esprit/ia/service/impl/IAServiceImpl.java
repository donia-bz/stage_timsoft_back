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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tn.esprit.ia.dto.CommandeDTO;
import tn.esprit.ia.dto.DispatchRequest;
import tn.esprit.ia.dto.DispatchResponse;

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
            delaiPreditMin = (int) Math.round((distanceKm / 30.0) * 60.0 + 15.0);
            if ("EXPRESS".equalsIgnoreCase(typeService)) {
                delaiPreditMin = Math.max(15, (int) (delaiPreditMin * 0.7));
            }
        } else {
            delaiPreditMin = 45;
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
    public AffectationIA calculerAffectation(String commandeId, Double latDepart, Double longDepart, List<LivreurDTO> livreurs) {
        if (livreurs == null || livreurs.isEmpty()) {
            throw new IllegalArgumentException("Aucun livreur disponible pour l'affectation IA");
        }

        LivreurDTO meilleurLivreur = null;
        double meilleurScore = -1.0;

        for (LivreurDTO livreur : livreurs) {
            double distanceKm = (latDepart != null && longDepart != null && livreur.getLatitudeActuelle() != null && livreur.getLongitudeActuelle() != null)
                    ? calculateHaversineDistance(latDepart, longDepart, livreur.getLatitudeActuelle(), livreur.getLongitudeActuelle())
                    : 5.0;

            double scoreProximite = 1.0 / (1.0 + distanceKm);
            double noteNorm = (livreur.getNoteMoyenne() != null ? livreur.getNoteMoyenne() : 5.0) / 5.0;
            double scoreGlobal = (scoreProximite * 0.7) + (noteNorm * 0.3);

            if (scoreGlobal > meilleurScore) {
                meilleurScore = scoreGlobal;
                meilleurLivreur = livreur;
            }
        }

        AffectationIA affectation = AffectationIA.builder()
                .commandeId(commandeId)
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
    public List<AffectationIA> getAffectationsByCommande(String commandeId) {
        return affectationIARepository.findByCommandeId(commandeId);
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    @Override
    public DispatchResponse dispatchGlobal(DispatchRequest request) {
        List<CommandeDTO> commandes = request.getCommandes();
        List<LivreurDTO> livreurs = request.getLivreurs();
        
        if (commandes == null || commandes.isEmpty() || livreurs == null || livreurs.isEmpty()) {
            return new DispatchResponse(new HashMap<>(), 0);
        }

        // K-Means clustering algorithm
        int k = Math.min(livreurs.size(), commandes.size());
        
        // 1. Initial centroids (randomly pick the first K commands' locations)
        double[][] centroids = new double[k][2];
        for (int i = 0; i < k; i++) {
            centroids[i][0] = commandes.get(i).getLatitude() != null ? commandes.get(i).getLatitude() : 36.8;
            centroids[i][1] = commandes.get(i).getLongitude() != null ? commandes.get(i).getLongitude() : 10.1;
        }
        
        Map<Integer, List<CommandeDTO>> clusters = new HashMap<>();
        
        // 2. Run iterations (max 10 for performance)
        for (int iter = 0; iter < 10; iter++) {
            clusters.clear();
            for (int i = 0; i < k; i++) {
                clusters.put(i, new ArrayList<>());
            }
            
            // Assign each command to the nearest centroid
            for (CommandeDTO cmd : commandes) {
                double lat = cmd.getLatitude() != null ? cmd.getLatitude() : 36.8;
                double lon = cmd.getLongitude() != null ? cmd.getLongitude() : 10.1;
                
                int bestCentroid = 0;
                double minDistance = Double.MAX_VALUE;
                for (int i = 0; i < k; i++) {
                    double dist = calculateHaversineDistance(lat, lon, centroids[i][0], centroids[i][1]);
                    if (dist < minDistance) {
                        minDistance = dist;
                        bestCentroid = i;
                    }
                }
                clusters.get(bestCentroid).add(cmd);
            }
            
            // Recalculate centroids
            for (int i = 0; i < k; i++) {
                List<CommandeDTO> clusterCmds = clusters.get(i);
                if (!clusterCmds.isEmpty()) {
                    double sumLat = 0;
                    double sumLon = 0;
                    for (CommandeDTO cmd : clusterCmds) {
                        sumLat += cmd.getLatitude() != null ? cmd.getLatitude() : 36.8;
                        sumLon += cmd.getLongitude() != null ? cmd.getLongitude() : 10.1;
                    }
                    centroids[i][0] = sumLat / clusterCmds.size();
                    centroids[i][1] = sumLon / clusterCmds.size();
                }
            }
        }
        
        // 3. Assign clusters to drivers based on proximity to centroid
        Map<String, List<String>> affectations = new HashMap<>();
        List<LivreurDTO> availableLivreurs = new ArrayList<>(livreurs);
        
        for (int i = 0; i < k; i++) {
            if (clusters.get(i).isEmpty()) continue;
            
            double centroidLat = centroids[i][0];
            double centroidLon = centroids[i][1];
            
            LivreurDTO bestLivreur = null;
            double minLivreurDist = Double.MAX_VALUE;
            
            for (LivreurDTO l : availableLivreurs) {
                double lLat = l.getLatitudeActuelle() != null ? l.getLatitudeActuelle() : 36.8;
                double lLon = l.getLongitudeActuelle() != null ? l.getLongitudeActuelle() : 10.1;
                double dist = calculateHaversineDistance(centroidLat, centroidLon, lLat, lLon);
                if (dist < minLivreurDist) {
                    minLivreurDist = dist;
                    bestLivreur = l;
                }
            }
            
            if (bestLivreur != null) {
                List<String> cmdIds = clusters.get(i).stream().map(CommandeDTO::getId).collect(Collectors.toList());
                affectations.put(bestLivreur.getId(), cmdIds);
                availableLivreurs.remove(bestLivreur); // Ensure 1 driver gets 1 cluster (simplification)
                
                // Save AffectationIA to DB
                for (String cmdId : cmdIds) {
                    AffectationIA aff = AffectationIA.builder()
                            .commandeId(cmdId)
                            .livreurId(bestLivreur.getId())
                            .score(Math.round((1.0 / (1.0 + minLivreurDist)) * 100.0) / 100.0)
                            .dateCalcul(LocalDateTime.now())
                            .build();
                    affectationIARepository.save(aff);
                }
            }
        }
        
        return new DispatchResponse(affectations, commandes.size());
    }
}
