package tn.esprit.notifications.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String message;
    private String type; // INFO, ALERTE, STATUT_LIVRAISON
    private LocalDateTime date;
    private Boolean lu;
}
