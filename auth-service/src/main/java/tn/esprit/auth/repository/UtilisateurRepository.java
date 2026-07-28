package tn.esprit.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.auth.entity.Utilisateur;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);
}
