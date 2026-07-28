package tn.esprit.ia.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ia.entity.PredictionDelai;

import java.util.List;

@Repository
public interface PredictionDelaiRepository extends MongoRepository<PredictionDelai, String> {
    List<PredictionDelai> findByCommandeId(String commandeId);
}
