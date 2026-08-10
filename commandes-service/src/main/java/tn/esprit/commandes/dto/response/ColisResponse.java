package tn.esprit.commandes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.commandes.entity.enums.StatutCommande;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisResponse {
    private String id;
    private String commandeId;
    private String clientId;
    private String destinataireId;
    private String depotId;
    private Double poids;
    private String dimensions;
    private Boolean fragile;
    private StatutCommande statut;
}
