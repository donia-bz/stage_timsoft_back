package tn.esprit.livreurs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.livreurs.entity.Depot;

@Repository
public interface DepotRepository extends MongoRepository<Depot, String> {
}
