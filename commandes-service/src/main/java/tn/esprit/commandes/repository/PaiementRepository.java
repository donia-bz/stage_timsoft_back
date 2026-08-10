package tn.esprit.commandes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.commandes.entity.Paiement;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementRepository extends MongoRepository<Paiement, String> {
    Optional<Paiement> findByCommandeId(String commandeId);
    List<Paiement> findByClientId(String clientId);

    @Query("{'clientId': ?0, 'dateCreation': {$gte: ?1, $lte: ?2}}")
    List<Paiement> findByClientIdAndDateCreationBetween(String clientId, String startDate, String endDate);
}
