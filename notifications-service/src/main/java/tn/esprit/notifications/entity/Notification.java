package tn.esprit.notifications.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.notifications.entity.enums.CanalNotification;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private String utilisateurId;
    private String titre;
    private String message;
    private String type; // INFO, ALERTE, STATUT_LIVRAISON
    private CanalNotification canal;
    private LocalDateTime date;
    private LocalDateTime dateLecture;
    private Boolean lu;
}
