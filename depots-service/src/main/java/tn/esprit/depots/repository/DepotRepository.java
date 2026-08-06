package tn.esprit.depots.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.depots.entity.Depot;

import java.util.List;

@Repository
public interface DepotRepository extends MongoRepository<Depot, String> {
    List<Depot> findByGouvernorat(String gouvernorat);
    List<Depot> findByVille(String ville);
    boolean existsByNom(String nom);
}