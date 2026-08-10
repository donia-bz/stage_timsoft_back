package tn.esprit.commandes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdresseRequest {

    @NotBlank(message = "Le gouvernorat est obligatoire")
    private String gouvernorat;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "La localité est obligatoire")
    private String localite;

    @NotBlank(message = "La rue est obligatoire")
    private String rue;

    private String codePostal;
    private String telephone;
    private Double latitude;
    private Double longitude;
}
