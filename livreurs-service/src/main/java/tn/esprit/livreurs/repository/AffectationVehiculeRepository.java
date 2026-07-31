package tn.esprit.livreurs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.livreurs.entity.AffectationVehicule;

import java.util.List;
import java.util.Optional;

@Repository
public interface AffectationVehiculeRepository extends MongoRepository<AffectationVehicule, String> {
    List<AffectationVehicule> findByLivreurIdOrderByDateDebutDesc(String livreurId);
    List<AffectationVehicule> findByVehiculeIdOrderByDateDebutDesc(String vehiculeId);
    Optional<AffectationVehicule> findByLivreurIdAndDateFinIsNull(String livreurId);
}
