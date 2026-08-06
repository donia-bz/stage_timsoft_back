package tn.esprit.reclamations.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reclamations")
public class Reclamation {

    @Id
    private String id;
    private String clientId;
    private String commandeId;
    private String type; // COLIS_ENDOMMAGE, RETARD, PAIEMENT, AUTRE
    private String description;
    private String statut; // EN_ATTENTE, EN_COURS, RESOLUE, REJETEE
    private String adminCommentaire;

    @CreatedDate
    private LocalDateTime dateCreation;
    private LocalDateTime dateResolution;
}