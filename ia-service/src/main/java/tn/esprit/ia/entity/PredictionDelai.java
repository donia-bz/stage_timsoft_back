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
@Document(collection = "predictions_delai")
public class PredictionDelai {

    @Id
    private String id;
    private String commandeId;
    private Integer delaiPreditMin;
    private LocalDateTime dateCalcul;
    private String versionModele;
}
