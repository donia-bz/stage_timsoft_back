package tn.esprit.notifications.service;

import tn.esprit.notifications.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification envoyerNotification(String utilisateurId, String message, String type);
    List<Notification> getNotificationsByUser(String utilisateurId);
    Notification marquerCommeLue(String id);
}
