package tn.esprit.commandes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.commandes.entity.Destinataire;

public interface DestinataireRepository extends MongoRepository<Destinataire, String> {
}
