package tn.esprit.notifications.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.notifications.entity.Notification;
import tn.esprit.notifications.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> envoyer(
            @RequestParam String utilisateurId,
            @RequestParam String message,
            @RequestParam(defaultValue = "INFO") String type) {
        Notification notification = notificationService.envoyerNotification(utilisateurId, message, type);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    @GetMapping("/user/{utilisateurId}")
    public ResponseEntity<List<Notification>> getByUser(@PathVariable String utilisateurId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(utilisateurId));
    }

    @PatchMapping("/{id}/lire")
    public ResponseEntity<Notification> marquerCommeLue(@PathVariable String id) {
        return ResponseEntity.ok(notificationService.marquerCommeLue(id));
    }
}
