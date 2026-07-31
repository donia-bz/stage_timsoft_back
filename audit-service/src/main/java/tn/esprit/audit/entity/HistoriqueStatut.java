package tn.esprit.audit.entity;

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
@Document(collection = "historique_statut")
public class HistoriqueStatut {

    @Id
    private String id;
    private String entiteType;
    private String entiteId;
    private String ancienStatut;
    private String nouveauStatut;
    private LocalDateTime dateChangement;
    private String auteurId;
}
