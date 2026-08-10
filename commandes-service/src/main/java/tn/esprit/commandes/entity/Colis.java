package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.commandes.entity.enums.StatutCommande;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "colis")
public class Colis {

    @Id
    private String id;

    private String commandeId; // Optionnel : null si envoi direct par Client
    private String clientId;   // Optionnel : requis si envoi direct par Client
    private String destinataireId;
    private String depotId;
    private Double poids;
    private String dimensions;
    private Boolean fragile;
    private StatutCommande statut; // Aligné avec StatutCommande pour un tracking unifié
}
