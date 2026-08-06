package tn.esprit.reclamations.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.reclamations.entity.Reclamation;

import java.util.List;

@Repository
public interface ReclamationRepository extends MongoRepository<Reclamation, String> {
    List<Reclamation> findByClientId(String clientId);
    List<Reclamation> findByCommandeId(String commandeId);
    List<Reclamation> findByStatut(String statut);
    List<Reclamation> findByType(String type);
}