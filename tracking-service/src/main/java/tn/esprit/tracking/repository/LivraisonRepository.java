package tn.esprit.tracking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tracking.entity.Livraison;

import java.util.List;

@Repository
public interface LivraisonRepository extends MongoRepository<Livraison, String> {
    List<Livraison> findByLivreurId(String livreurId);
    List<Livraison> findByColisId(String colisId);
}
