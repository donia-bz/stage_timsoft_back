package tn.esprit.commandes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.commandes.entity.Manifeste;

import java.util.List;

public interface ManifesteRepository extends MongoRepository<Manifeste, String> {
    List<Manifeste> findByClientId(String clientId);
}
