package tn.esprit.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.esprit.auth.entity.Admin;
import tn.esprit.auth.entity.Client;
import tn.esprit.auth.entity.Livreur;
import tn.esprit.auth.entity.enums.StatutLivreur;
import tn.esprit.auth.repository.UtilisateurRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Nettoyer toutes les données existantes
        System.out.println("🧹 Nettoyage complet de la base de données...");
        utilisateurRepository.deleteAll();
        System.out.println("✅ Base de données nettoyée");

        // Créer un compte admin par défaut
        Admin admin = Admin.builder()
                .nom("Admin")
                .prenom("BFExpress")
                .email("admin@bfexpress.com")
                .motDePasseHash(passwordEncoder.encode("admin123"))
                .telephone("71123456")
                .role("ADMIN")
                .dateCreation(LocalDateTime.now())
                .niveauAcces("SUPER_ADMIN")
                .build();
        
        utilisateurRepository.save(admin);
        System.out.println("✅ Compte admin créé : admin@bfexpress.com / admin123");

        // Créer un compte client de test avec des informations pro
        Client client = Client.builder()
                .nom("Bouzouita")
                .prenom("Chirine")
                .email("client@bfexpress.com")
                .motDePasseHash(passwordEncoder.encode("client123"))
                .telephone("71234567")
                .role("CLIENT")
                .dateCreation(LocalDateTime.now())
                .entreprise("BFExpress SARL")
                .matriculeFiscal("1234567/A/M/000")
                .build();
        
        Client savedClient = utilisateurRepository.save(client);
        System.out.println("✅ Compte client créé : client@bfexpress.com / client123");
        System.out.println("   ID Client : " + savedClient.getId());

        // Créer un compte livreur de test
        Livreur livreur = Livreur.builder()
                .nom("Livreur")
                .prenom("Test")
                .email("livreur@bfexpress.com")
                .motDePasseHash(passwordEncoder.encode("livreur123"))
                .telephone("71345678")
                .role("LIVREUR")
                .dateCreation(LocalDateTime.now())
                .statut(StatutLivreur.DISPONIBLE)
                .latitudeActuelle(36.8065f)
                .longitudeActuelle(10.1815f)
                .noteMoyenne(5.0f)
                .build();
        
        Livreur savedLivreur = utilisateurRepository.save(livreur);
        System.out.println("✅ Compte livreur créé : livreur@bfexpress.com / livreur123");
        System.out.println("   ID Livreur : " + savedLivreur.getId());

        System.out.println("🎉 Initialisation des données de test terminée !");
        System.out.println("📋 Identifiants de connexion :");
        System.out.println("   ADMIN : admin@bfexpress.com / admin123");
        System.out.println("   CLIENT : client@bfexpress.com / client123");
        System.out.println("   LIVREUR : livreur@bfexpress.com / livreur123");
    }
}