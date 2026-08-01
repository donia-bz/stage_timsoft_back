package tn.esprit.commandes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.commandes.entity.Enlevement;

import java.util.List;

public interface EnlevementRepository extends MongoRepository<Enlevement, String> {
    List<Enlevement> findByClientId(String clientId);
    List<Enlevement> findByLivreurId(String livreurId);
}
