package tn.esprit.tracking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tracking.entity.PositionTracking;

import java.util.List;

@Repository
public interface PositionTrackingRepository extends MongoRepository<PositionTracking, String> {
    List<PositionTracking> findByLivraisonIdOrderByHorodatageAsc(String livraisonId);
}
