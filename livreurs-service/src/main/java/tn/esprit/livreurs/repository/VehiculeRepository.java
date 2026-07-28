package tn.esprit.livreurs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.livreurs.entity.Vehicule;

import java.util.Optional;

@Repository
public interface VehiculeRepository extends MongoRepository<Vehicule, String> {
    Optional<Vehicule> findByLivreurId(String livreurId);
}
