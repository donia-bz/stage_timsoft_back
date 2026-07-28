package tn.esprit.commandes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.commandes.entity.Colis;

import java.util.List;

public interface ColisRepository extends MongoRepository<Colis, String> {

    List<Colis> findByCommandeId(String commandeId);
}
