package tn.esprit.tracking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.tracking.entity.Livraison;
import tn.esprit.tracking.entity.PositionTracking;
import tn.esprit.tracking.entity.enums.StatutLivraison;
import tn.esprit.tracking.repository.LivraisonRepository;
import tn.esprit.tracking.repository.PositionTrackingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackingDataInitializer implements CommandLineRunner {

    private final LivraisonRepository livraisonRepository;
    private final PositionTrackingRepository positionTrackingRepository;

    @Override
    public void run(String... args) {
        System.out.println("🚀 Initialisation des données de test pour le service tracking...");

        // Nettoyer les données existantes
        System.out.println("🧹 Nettoyage des données existantes...");
        positionTrackingRepository.deleteAll();
        livraisonRepository.deleteAll();
        System.out.println("✅ Base de données nettoyée");

        String livreurId = "livreur-test-id";
        String clientId = "client-test-id";

        // Créer des livraisons avec différents statuts pour le suivi
        // Livraison 1 : En cours (active) - pour le tracking GPS en temps réel
        Livraison livraison1 = Livraison.builder()
                .colisId("colis-1")
                .livreurId(livreurId)
                .statut(StatutLivraison.EN_COURS)
                .dateAffectation(LocalDateTime.now().minusHours(1))
                .dateDebut(LocalDateTime.now().minusMinutes(30))
                .distanceKm(5.2)
                .build();

        // Livraison 2 : Affectée (en attente de démarrage)
        Livraison livraison2 = Livraison.builder()
                .colisId("colis-2")
                .livreurId(livreurId)
                .statut(StatutLivraison.AFFECTEE)
                .dateAffectation(LocalDateTime.now().minusMinutes(15))
                .distanceKm(8.5)
                .build();

        // Livraison 3 : Livrée (terminée avec succès)
        Livraison livraison3 = Livraison.builder()
                .colisId("colis-3")
                .livreurId(livreurId)
                .statut(StatutLivraison.LIVREE)
                .dateAffectation(LocalDateTime.now().minusHours(3))
                .dateDebut(LocalDateTime.now().minusHours(2))
                .dateFin(LocalDateTime.now().minusHours(1))
                .distanceKm(12.3)
                .build();

        // Livraison 4 : Échouée (problème de livraison)
        Livraison livraison4 = Livraison.builder()
                .colisId("colis-4")
                .livreurId(livreurId)
                .statut(StatutLivraison.ECHOUEE)
                .dateAffectation(LocalDateTime.now().minusHours(5))
                .dateDebut(LocalDateTime.now().minusHours(4))
                .distanceKm(7.8)
                .build();

        // Livraison 5 : En cours (deuxième livraison active)
        Livraison livraison5 = Livraison.builder()
                .colisId("colis-5")
                .livreurId(livreurId)
                .statut(StatutLivraison.EN_COURS)
                .dateAffectation(LocalDateTime.now().minusMinutes(45))
                .dateDebut(LocalDateTime.now().minusMinutes(20))
                .distanceKm(3.8)
                .build();

        // Livraison 6 : Affectée (en attente)
        Livraison livraison6 = Livraison.builder()
                .colisId("colis-6")
                .livreurId(livreurId)
                .statut(StatutLivraison.AFFECTEE)
                .dateAffectation(LocalDateTime.now().minusMinutes(5))
                .distanceKm(6.2)
                .build();

        livraison1 = livraisonRepository.save(livraison1);
        livraison2 = livraisonRepository.save(livraison2);
        livraison3 = livraisonRepository.save(livraison3);
        livraison4 = livraisonRepository.save(livraison4);
        livraison5 = livraisonRepository.save(livraison5);
        livraison6 = livraisonRepository.save(livraison6);

        System.out.println("✅ Livraisons créées (6 livraisons avec différents statuts)");

        // Créer des positions GPS pour le tracking en temps réel
        List<PositionTracking> positions = new ArrayList<>();

        // Positions pour livraison 1 (en cours - tracking détaillé)
        positions.add(PositionTracking.builder()
                .livraisonId(livraison1.getId())
                .latitude(36.8065)
                .longitude(10.1815)
                .horodatage(LocalDateTime.now().minusMinutes(30))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison1.getId())
                .latitude(36.8100)
                .longitude(10.1850)
                .horodatage(LocalDateTime.now().minusMinutes(25))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison1.getId())
                .latitude(36.8150)
                .longitude(10.1900)
                .horodatage(LocalDateTime.now().minusMinutes(20))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison1.getId())
                .latitude(36.8200)
                .longitude(10.1950)
                .horodatage(LocalDateTime.now().minusMinutes(15))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison1.getId())
                .latitude(36.8250)
                .longitude(10.2000)
                .horodatage(LocalDateTime.now().minusMinutes(10))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison1.getId())
                .latitude(36.8300)
                .longitude(10.2050)
                .horodatage(LocalDateTime.now().minusMinutes(5))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison1.getId())
                .latitude(36.8350)
                .longitude(10.2100)
                .horodatage(LocalDateTime.now())
                .build());

        // Positions pour livraison 5 (en cours - tracking détaillé)
        positions.add(PositionTracking.builder()
                .livraisonId(livraison5.getId())
                .latitude(36.7900)
                .longitude(10.1700)
                .horodatage(LocalDateTime.now().minusMinutes(20))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison5.getId())
                .latitude(36.7950)
                .longitude(10.1750)
                .horodatage(LocalDateTime.now().minusMinutes(15))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison5.getId())
                .latitude(36.8000)
                .longitude(10.1800)
                .horodatage(LocalDateTime.now().minusMinutes(10))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison5.getId())
                .latitude(36.8050)
                .longitude(10.1850)
                .horodatage(LocalDateTime.now().minusMinutes(5))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison5.getId())
                .latitude(36.8100)
                .longitude(10.1900)
                .horodatage(LocalDateTime.now())
                .build());

        // Positions pour livraison 3 (livrée - tracking historique)
        positions.add(PositionTracking.builder()
                .livraisonId(livraison3.getId())
                .latitude(36.8000)
                .longitude(10.2000)
                .horodatage(LocalDateTime.now().minusHours(2))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison3.getId())
                .latitude(36.8100)
                .longitude(10.2100)
                .horodatage(LocalDateTime.now().minusHours(1))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison3.getId())
                .latitude(36.8200)
                .longitude(10.2200)
                .horodatage(LocalDateTime.now().minusMinutes(30))
                .build());

        positions.add(PositionTracking.builder()
                .livraisonId(livraison3.getId())
                .latitude(36.8300)
                .longitude(10.2300)
                .horodatage(LocalDateTime.now())
                .build());

        positionTrackingRepository.saveAll(positions);

        System.out.println("✅ Positions GPS créées (17 positions pour tracking détaillé)");

        System.out.println("🎉 Initialisation des données de test terminée !");
        System.out.println("📊 Résumé des données de tracking créées :");
        System.out.println("   - 6 livraisons (EN_COURS x2, AFFECTEE x2, LIVREE, ECHOUEE)");
        System.out.println("   - 17 positions GPS pour tracking en temps réel");
        System.out.println("🎯 Données optimisées pour le suivi de colis dans le tableau de bord !");
    }
}