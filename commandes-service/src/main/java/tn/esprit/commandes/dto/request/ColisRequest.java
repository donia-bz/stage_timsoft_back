package tn.esprit.commandes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ColisRequest {

    @NotNull(message = "Le poids est obligatoire")
    @Positive(message = "Le poids doit etre positif")
    private Double poids;

    private String dimensions;
    private Boolean fragile;
}
