package tn.esprit.vehicles.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.vehicles.entity.Vehicule;

import java.util.List;

@Repository
public interface VehiculeRepository extends MongoRepository<Vehicule, String> {
    List<Vehicule> findByStatut(String statut);
    List<Vehicule> findByLivreurId(String livreurId);
    List<Vehicule> findByType(String type);
    boolean existsByImmatriculation(String immatriculation);
}