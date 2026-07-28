package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.commandes.entity.enums.StatutColis;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "colis")
public class Colis {

    @Id
    private String id;

    private String commandeId;
    private Double poids;
    private String dimensions;
    private Boolean fragile;
    private StatutColis statut;
}
