package tn.esprit.commandes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.commandes.entity.Paiement;

import java.util.Optional;

@Repository
public interface PaiementRepository extends MongoRepository<Paiement, String> {
    Optional<Paiement> findByCommandeId(String commandeId);
}
