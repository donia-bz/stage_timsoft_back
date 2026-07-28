package tn.esprit.notifications.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.notifications.entity.Notification;
import tn.esprit.notifications.repository.NotificationRepository;
import tn.esprit.notifications.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification envoyerNotification(String utilisateurId, String message, String type) {
        Notification notification = Notification.builder()
                .utilisateurId(utilisateurId)
                .message(message)
                .type(type != null ? type : "INFO")
                .date(LocalDateTime.now())
                .lu(false)
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByUser(String utilisateurId) {
        return notificationRepository.findByUtilisateurIdOrderByDateDesc(utilisateurId);
    }

    @Override
    public Notification marquerCommeLue(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification introuvable avec l'id : " + id));
        notification.setLu(true);
        return notificationRepository.save(notification);
    }
}
