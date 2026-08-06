package tn.esprit.stats.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.stats.entity.EvaluationLivreur;

import java.util.List;

@Repository
public interface EvaluationLivreurRepository extends MongoRepository<EvaluationLivreur, String> {
    List<EvaluationLivreur> findByLivreurId(String livreurId);
    List<EvaluationLivreur> findByClientId(String clientId);
    List<EvaluationLivreur> findByCommandeId(String commandeId);
}