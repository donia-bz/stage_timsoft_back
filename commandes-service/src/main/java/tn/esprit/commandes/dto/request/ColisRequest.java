package tn.esprit.commandes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ColisRequest {

    private String commandeId; // Optionnel : null si envoi direct
    private String clientId;   // Optionnel : requis si envoi direct

    @NotNull(message = "Le poids est obligatoire")
    @Positive(message = "Le poids doit etre positif")
    private Double poids;

    private String dimensions;
    private Boolean fragile;
    private String destinataireId;
    private String depotId;
}
