package tn.esprit.livreurs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.livreurs.entity.Livreur;

import java.util.List;

@Repository
public interface LivreurRepository extends MongoRepository<Livreur, String> {
    List<Livreur> findByStatut(String statut);
}
