package tn.esprit.audit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.audit.entity.Evaluation;

import java.util.List;

@Repository
public interface EvaluationRepository extends MongoRepository<Evaluation, String> {
    List<Evaluation> findByLivraisonId(String livraisonId);
    List<Evaluation> findByClientId(String clientId);
}
