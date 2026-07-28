package tn.esprit.notifications.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.notifications.entity.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUtilisateurIdOrderByDateDesc(String utilisateurId);
}
