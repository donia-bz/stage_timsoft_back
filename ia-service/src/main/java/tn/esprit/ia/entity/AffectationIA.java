package tn.esprit.ia.entity;

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
@Document(collection = "affectations_ia")
public class AffectationIA {

    @Id
    private String id;
    private String commandeId;
    private String livreurId;
    private Double score;
    private LocalDateTime dateCalcul;
}
