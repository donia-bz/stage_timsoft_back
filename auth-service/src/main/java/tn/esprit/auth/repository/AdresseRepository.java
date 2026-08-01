package tn.esprit.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.auth.entity.Adresse;

public interface AdresseRepository extends MongoRepository<Adresse, String> {
}
