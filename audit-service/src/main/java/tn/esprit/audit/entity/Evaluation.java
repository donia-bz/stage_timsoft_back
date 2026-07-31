package tn.esprit.audit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.audit.entity.enums.TypeEvaluation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "evaluations")
public class Evaluation {

    @Id
    private String id;
    private Integer note;
    private String commentaire;
    private TypeEvaluation type;
    private String livraisonId;
    private String clientId;
}
