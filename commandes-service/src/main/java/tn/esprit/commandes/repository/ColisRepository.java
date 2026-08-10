package tn.esprit.commandes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.commandes.entity.Colis;

import java.util.List;

public interface ColisRepository extends MongoRepository<Colis, String> {

    List<Colis> findByCommandeId(String commandeId);
    List<Colis> findByCommandeIdIn(List<String> commandeIds);
    List<Colis> findByClientId(String clientId);
    List<Colis> findByDestinataireId(String destinataireId);
    List<Colis> findByDepotId(String depotId);
}
