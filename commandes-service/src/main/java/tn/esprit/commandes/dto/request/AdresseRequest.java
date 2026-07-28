package tn.esprit.commandes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdresseRequest {

    @NotBlank(message = "La rue est obligatoire")
    private String rue;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    private String codePostal;
    private Double latitude;
    private Double longitude;
}
