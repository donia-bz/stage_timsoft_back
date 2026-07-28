package tn.esprit.commandes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.commandes.entity.Commande;
import tn.esprit.commandes.entity.enums.StatutCommande;

import java.util.List;

public interface CommandeRepository extends MongoRepository<Commande, String> {

    List<Commande> findByClientId(String clientId);

    List<Commande> findByStatut(StatutCommande statut);
}
