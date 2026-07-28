package tn.esprit.ia.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ia.entity.AffectationIA;

import java.util.List;

@Repository
public interface AffectationIARepository extends MongoRepository<AffectationIA, String> {
    List<AffectationIA> findByColisId(String colisId);
}
