package tn.esprit.audit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.audit.entity.HistoriqueStatut;

import java.util.List;

@Repository
public interface HistoriqueStatutRepository extends MongoRepository<HistoriqueStatut, String> {
    List<HistoriqueStatut> findByEntiteTypeAndEntiteIdOrderByDateChangementDesc(String entiteType, String entiteId);
    List<HistoriqueStatut> findByAuteurIdOrderByDateChangementDesc(String auteurId);
}
