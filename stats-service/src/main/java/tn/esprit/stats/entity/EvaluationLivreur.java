package tn.esprit.stats.entity;

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
@Document(collection = "evaluations")
public class EvaluationLivreur {

    @Id
    private String id;
    private String livreurId;
    private String clientId;
    private String commandeId;
    private Integer note; // 1-5
    private String commentaire;

    @CreatedDate
    private LocalDateTime date;
}