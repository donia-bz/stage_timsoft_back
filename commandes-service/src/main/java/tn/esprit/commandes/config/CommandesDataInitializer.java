package tn.esprit.commandes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.commandes.repository.CommandeRepository;
import tn.esprit.commandes.repository.ColisRepository;
import tn.esprit.commandes.repository.DestinataireRepository;
import tn.esprit.commandes.repository.ManifesteRepository;
import tn.esprit.commandes.repository.EnlevementRepository;

@Component
@RequiredArgsConstructor
public class CommandesDataInitializer implements CommandLineRunner {

    private final CommandeRepository commandeRepository;
    private final ColisRepository colisRepository;
    private final DestinataireRepository destinataireRepository;
    private final ManifesteRepository manifesteRepository;
    private final EnlevementRepository enlevementRepository;

    @Override
    public void run(String... args) {
        System.out.println("🚀 Initialisation des données de test pour le service commandes...");

        // Nettoyer les données existantes (désactivé pour conserver les données)
        // System.out.println("🧹 Nettoyage des données existantes...");
        // enlevementRepository.deleteAll();
        // manifesteRepository.deleteAll();
        // colisRepository.deleteAll();
        // commandeRepository.deleteAll();
        // destinataireRepository.deleteAll();
        // System.out.println("✅ Base de données nettoyée");

        // Ne pas créer de données de test - les commandes seront créées par les utilisateurs
        System.out.println("📋 Aucune donnée de test créée - les utilisateurs créeront leurs propres commandes");
        System.out.println("🎉 Initialisation terminée !");
    }
}
