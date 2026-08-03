package tn.esprit.livreurs.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.livreurs.entity.Depot;
import tn.esprit.livreurs.entity.Livreur;
import tn.esprit.livreurs.entity.enums.StatutLivreur;
import tn.esprit.livreurs.repository.DepotRepository;
import tn.esprit.livreurs.repository.LivreurRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LivreursDataInitializer implements CommandLineRunner {

    private final LivreurRepository livreurRepository;
    private final DepotRepository depotRepository;

    @Override
    public void run(String... args) {
        System.out.println("🚀 Initialisation des données de test pour le service livreurs...");

        // Nettoyer les données existantes
        System.out.println("🧹 Nettoyage des données existantes...");
        livreurRepository.deleteAll();
        depotRepository.deleteAll();
        System.out.println("✅ Base de données nettoyée");

        // Créer des dépôts
        Depot depot1 = Depot.builder()
                .nom("Dépôt Central Tunis")
                .adresseId("adresse-depot-tunis")
                .capacite(1000)
                .build();

        Depot depot2 = Depot.builder()
                .nom("Dépôt Sfax")
                .adresseId("adresse-depot-sfax")
                .capacite(500)
                .build();

        depot1 = depotRepository.save(depot1);
        depot2 = depotRepository.save(depot2);

        System.out.println("✅ Dépôts créés");

        // Créer des livreurs avec différents statuts
        // Livreur 1 : Disponible
        Livreur livreur1 = Livreur.builder()
                .nom("Ben")
                .prenom("Ahmed")
                .email("ahmed.ben@bfexpress.com")
                .motDePasseHash("$2a$10$encodedpassword1")
                .telephone("20123456")
                .role("LIVREUR")
                .dateCreation(LocalDateTime.now())
                .statut(StatutLivreur.DISPONIBLE)
                .latitudeActuelle(36.8065f)
                .longitudeActuelle(10.1815f)
                .depotId(depot1.getId())
                .noteMoyenne(4.8f)
                .build();

        // Livreur 2 : En course
        Livreur livreur2 = Livreur.builder()
                .nom("Trabelsi")
                .prenom("Fatma")
                .email("fatma.trabelsi@bfexpress.com")
                .motDePasseHash("$2a$10$encodedpassword2")
                .telephone("70987654")
                .role("LIVREUR")
                .dateCreation(LocalDateTime.now().minusDays(30))
                .statut(StatutLivreur.EN_COURSE)
                .latitudeActuelle(36.8200f)
                .longitudeActuelle(10.1900f)
                .depotId(depot1.getId())
                .noteMoyenne(4.9f)
                .build();

        // Livreur 3 : Hors ligne
        Livreur livreur3 = Livreur.builder()
                .nom("Karray")
                .prenom("Mohamed")
                .email("mohamed.karray@bfexpress.com")
                .motDePasseHash("$2a$10$encodedpassword3")
                .telephone("22345678")
                .role("LIVREUR")
                .dateCreation(LocalDateTime.now().minusDays(60))
                .statut(StatutLivreur.HORS_LIGNE)
                .latitudeActuelle(36.8000f)
                .longitudeActuelle(10.2000f)
                .depotId(depot2.getId())
                .noteMoyenne(4.5f)
                .build();

        livreur1 = livreurRepository.save(livreur1);
        livreur2 = livreurRepository.save(livreur2);
        livreur3 = livreurRepository.save(livreur3);

        System.out.println("✅ Livreurs créés");

        System.out.println("🎉 Initialisation des données de test terminée !");
        System.out.println("📊 Résumé des données créées :");
        System.out.println("   - 2 dépôts (Tunis, Sfax)");
        System.out.println("   - 3 livreurs (DISPONIBLE, EN_COURSE, HORS_LIGNE)");
    }
}