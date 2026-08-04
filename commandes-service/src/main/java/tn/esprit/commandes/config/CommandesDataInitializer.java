package tn.esprit.commandes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.commandes.entity.*;
import tn.esprit.commandes.entity.enums.StatutColis;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.entity.enums.StatutEnlevement;
import tn.esprit.commandes.entity.enums.StatutManifeste;
import tn.esprit.commandes.repository.CommandeRepository;
import tn.esprit.commandes.repository.ColisRepository;
import tn.esprit.commandes.repository.DestinataireRepository;
import tn.esprit.commandes.repository.ManifesteRepository;
import tn.esprit.commandes.repository.EnlevementRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandesDataInitializer implements CommandLineRunner {

    // private final AdresseRepository adresseRepository; // Commenté car ce n'est pas dans ce service
    private final CommandeRepository commandeRepository;
    private final ColisRepository colisRepository;
    private final DestinataireRepository destinataireRepository;
    private final ManifesteRepository manifesteRepository;
    private final EnlevementRepository enlevementRepository;

    @Override
    public void run(String... args) {
        System.out.println("🚀 Initialisation des données de test pour le service commandes...");

        // Nettoyer les données existantes
        System.out.println("🧹 Nettoyage des données existantes...");
        enlevementRepository.deleteAll();
        manifesteRepository.deleteAll();
        colisRepository.deleteAll();
        commandeRepository.deleteAll();
        destinataireRepository.deleteAll();
        // adresseRepository.deleteAll(); // Commenté car ce n'est pas dans ce service
        System.out.println("✅ Base de données nettoyée");

        // Créer des adresses pour le client test
        String clientId = "client-test-id-temp"; // sera synchronisé avec l'ID du client
        
        // Adresses du client (simulées avec IDs)
        String adresseClient1Id = "addr-1";
        String adresseClient2Id = "addr-2";
        String adresseClient3Id = "addr-3";
        String adresseClient4Id = "addr-4";
        String adresseDepartId = "addr-depart";
        
        System.out.println("✅ Adresses simulées (5 adresses)");

        // Créer des destinataires variés
        Destinataire dest1 = Destinataire.builder()
                .nom("Ahmed Ben Ali")
                .telephone("20123456")
                .adresseId(adresseClient1Id)
                .build();
        
        Destinataire dest2 = Destinataire.builder()
                .nom("Fatma Trabelsi")
                .telephone("70987654")
                .adresseId(adresseClient2Id)
                .build();
        
        Destinataire dest3 = Destinataire.builder()
                .nom("Mohamed Karray")
                .telephone("22345678")
                .adresseId(adresseClient3Id)
                .build();
        
        Destinataire dest4 = Destinataire.builder()
                .nom("Samia Jaziri")
                .telephone("98765432")
                .adresseId(adresseClient4Id)
                .build();
        
        Destinataire dest5 = Destinataire.builder()
                .nom("Khaled Mejri")
                .telephone("99887766")
                .adresseId(adresseClient1Id)
                .build();
        
        dest1 = destinataireRepository.save(dest1);
        dest2 = destinataireRepository.save(dest2);
        dest3 = destinataireRepository.save(dest3);
        dest4 = destinataireRepository.save(dest4);
        dest5 = destinataireRepository.save(dest5);
        
        System.out.println("✅ Destinataires créés (5 destinataires)");

        // Créer des commandes avec différents statuts pour le tableau de bord
        // Commande 1 : En attente - Commande récente
        Commande cmd1 = Commande.builder()
                .clientId(clientId)
                .adresseDepartId(adresseDepartId)
                .adresseArriveeId(adresseClient1Id)
                .statut(StatutCommande.EN_ATTENTE)
                .typeService("STANDARD")
                .dateCreation(LocalDateTime.now().minusMinutes(30))
                .delaiEstimeMin(45)
                .montantTotal(25.0)
                .build();
        
        // Commande 2 : En livraison - Commande active
        Commande cmd2 = Commande.builder()
                .clientId(clientId)
                .adresseDepartId(adresseDepartId)
                .adresseArriveeId(adresseClient2Id)
                .statut(StatutCommande.EN_LIVRAISON)
                .typeService("EXPRESS")
                .dateCreation(LocalDateTime.now().minusHours(2))
                .delaiEstimeMin(30)
                .montantTotal(35.0)
                .build();
        
        // Commande 3 : Livrée - Commande terminée
        Commande cmd3 = Commande.builder()
                .clientId(clientId)
                .adresseDepartId(adresseDepartId)
                .adresseArriveeId(adresseClient3Id)
                .statut(StatutCommande.LIVREE)
                .typeService("STANDARD")
                .dateCreation(LocalDateTime.now().minusDays(1))
                .delaiEstimeMin(60)
                .montantTotal(28.0)
                .build();
        
        // Commande 4 : Confirmée - Prête pour enlèvement
        Commande cmd4 = Commande.builder()
                .clientId(clientId)
                .adresseDepartId(adresseDepartId)
                .adresseArriveeId(adresseClient4Id)
                .statut(StatutCommande.CONFIRMEE)
                .typeService("STANDARD")
                .dateCreation(LocalDateTime.now().minusHours(1))
                .delaiEstimeMin(50)
                .montantTotal(30.0)
                .build();
        
        // Commande 5 : En attente - Commande récente
        Commande cmd5 = Commande.builder()
                .clientId(clientId)
                .adresseDepartId(adresseDepartId)
                .adresseArriveeId(adresseClient2Id)
                .statut(StatutCommande.EN_ATTENTE)
                .typeService("STANDARD")
                .dateCreation(LocalDateTime.now().minusMinutes(15))
                .delaiEstimeMin(40)
                .montantTotal(22.0)
                .build();
        
        // Commande 6 : Livrée - Ancienne commande
        Commande cmd6 = Commande.builder()
                .clientId(clientId)
                .adresseDepartId(adresseDepartId)
                .adresseArriveeId(adresseClient1Id)
                .statut(StatutCommande.LIVREE)
                .typeService("EXPRESS")
                .dateCreation(LocalDateTime.now().minusDays(3))
                .delaiEstimeMin(25)
                .montantTotal(40.0)
                .build();
        
        cmd1 = commandeRepository.save(cmd1);
        cmd2 = commandeRepository.save(cmd2);
        cmd3 = commandeRepository.save(cmd3);
        cmd4 = commandeRepository.save(cmd4);
        cmd5 = commandeRepository.save(cmd5);
        cmd6 = commandeRepository.save(cmd6);
        
        System.out.println("✅ Commandes créées (6 commandes avec différents statuts)");

        // Créer des colis avec différents statuts pour le suivi
        // Colis pour commande 1 (en attente)
        Colis colis1 = Colis.builder()
                .commandeId(cmd1.getId())
                .clientId(clientId)
                .destinataireId(dest1.getId())
                .poids(2.5)
                .dimensions("30x20x15")
                .fragile(false)
                .statut(StatutColis.EN_ATTENTE)
                .build();
        
        // Colis pour commande 2 (en transit - livraison active)
        Colis colis2 = Colis.builder()
                .commandeId(cmd2.getId())
                .clientId(clientId)
                .destinataireId(dest2.getId())
                .poids(1.8)
                .dimensions("25x15x10")
                .fragile(true)
                .statut(StatutColis.EN_TRANSIT)
                .build();
        
        // Colis pour commande 3 (livré)
        Colis colis3 = Colis.builder()
                .commandeId(cmd3.getId())
                .clientId(clientId)
                .destinataireId(dest3.getId())
                .poids(3.2)
                .dimensions("40x30x20")
                .fragile(false)
                .statut(StatutColis.LIVRE)
                .build();
        
        // Colis pour commande 4 (à enlever)
        Colis colis4 = Colis.builder()
                .commandeId(cmd4.getId())
                .clientId(clientId)
                .destinataireId(dest4.getId())
                .poids(2.0)
                .dimensions("35x25x15")
                .fragile(false)
                .statut(StatutColis.A_ENLEVER)
                .build();
        
        // Colis pour commande 5 (en attente)
        Colis colis5 = Colis.builder()
                .commandeId(cmd5.getId())
                .clientId(clientId)
                .destinataireId(dest2.getId())
                .poids(1.5)
                .dimensions("20x15x10")
                .fragile(false)
                .statut(StatutColis.EN_ATTENTE)
                .build();
        
        // Colis pour commande 6 (livré payé)
        Colis colis6 = Colis.builder()
                .commandeId(cmd6.getId())
                .clientId(clientId)
                .destinataireId(dest1.getId())
                .poids(4.0)
                .dimensions("50x40x30")
                .fragile(true)
                .statut(StatutColis.LIVRE_PAYE)
                .build();
        
        // Colis autonome (sans commande) - En attente
        Colis colis7 = Colis.builder()
                .clientId(clientId)
                .destinataireId(dest3.getId())
                .poids(1.2)
                .dimensions("18x12x8")
                .fragile(false)
                .statut(StatutColis.EN_ATTENTE)
                .build();
        
        // Colis autonome - Au dépôt
        Colis colis8 = Colis.builder()
                .clientId(clientId)
                .destinataireId(dest4.getId())
                .poids(5.5)
                .dimensions("60x50x40")
                .fragile(false)
                .statut(StatutColis.AU_DEPOT)
                .build();
        
        // Colis autonome - Non sérieux
        Colis colis9 = Colis.builder()
                .clientId(clientId)
                .destinataireId(dest5.getId())
                .poids(0.8)
                .dimensions("15x10x5")
                .fragile(false)
                .statut(StatutColis.NON_SERIEUX)
                .build();
        
        // Colis autonome - À vérifier
        Colis colis10 = Colis.builder()
                .clientId(clientId)
                .destinataireId(dest1.getId())
                .poids(2.8)
                .dimensions("32x22x18")
                .fragile(true)
                .statut(StatutColis.A_VERIFIER)
                .build();
        
        colis1 = colisRepository.save(colis1);
        colis2 = colisRepository.save(colis2);
        colis3 = colisRepository.save(colis3);
        colis4 = colisRepository.save(colis4);
        colis5 = colisRepository.save(colis5);
        colis6 = colisRepository.save(colis6);
        colis7 = colisRepository.save(colis7);
        colis8 = colisRepository.save(colis8);
        colis9 = colisRepository.save(colis9);
        colis10 = colisRepository.save(colis10);
        
        System.out.println("✅ Colis créés (10 colis avec différents statuts de suivi)");

        // Créer des manifestes
        Manifeste manifeste1 = Manifeste.builder()
                .clientId(clientId)
                .nombreColis(4)
                .statut(StatutManifeste.BROUILLON)
                .colisIds(List.of(colis1.getId(), colis4.getId(), colis5.getId(), colis7.getId()))
                .dateCreation(LocalDateTime.now())
                .build();
        
        Manifeste manifeste2 = Manifeste.builder()
                .clientId(clientId)
                .nombreColis(3)
                .statut(StatutManifeste.IMPRIME)
                .colisIds(List.of(colis2.getId(), colis3.getId(), colis6.getId()))
                .dateCreation(LocalDateTime.now().minusHours(3))
                .build();
        
        manifeste1 = manifesteRepository.save(manifeste1);
        manifeste2 = manifesteRepository.save(manifeste2);
        
        System.out.println("✅ Manifestes créés (2 manifestes)");

        // Créer des enlèvements
        Enlevement enlevement1 = Enlevement.builder()
                .clientId(clientId)
                .manifesteId(manifeste1.getId())
                .dateDemandee(LocalDateTime.now().plusDays(1))
                .statut(StatutEnlevement.EN_ATTENTE)
                .adresseEnlevementId(adresseClient1Id)
                .build();
        
        Enlevement enlevement2 = Enlevement.builder()
                .clientId(clientId)
                .manifesteId(manifeste2.getId())
                .dateDemandee(LocalDateTime.now().minusHours(2))
                .dateReelle(LocalDateTime.now().minusHours(1))
                .statut(StatutEnlevement.EFFECTUE)
                .adresseEnlevementId(adresseClient2Id)
                .build();
        
        enlevement1 = enlevementRepository.save(enlevement1);
        enlevement2 = enlevementRepository.save(enlevement2);
        
        System.out.println("✅ Enlèvements créés (2 enlèvements)");

        System.out.println("🎉 Initialisation des données de test terminée !");
        System.out.println("📊 Résumé des données créées pour le suivi de colis :");
        System.out.println("   - 5 destinataires");
        System.out.println("   - 6 commandes (EN_ATTENTE, EN_LIVRAISON, LIVREE, CONFIRMEE)");
        System.out.println("   - 10 colis (différents statuts pour le suivi)");
        System.out.println("   - 2 manifestes");
        System.out.println("   - 2 enlèvements");
        System.out.println("🎯 Données optimisées pour le tableau de bord client !");
    }
}