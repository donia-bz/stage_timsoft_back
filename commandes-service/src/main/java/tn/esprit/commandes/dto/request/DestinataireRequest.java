package tn.esprit.commandes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DestinataireRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le telephone est obligatoire")
    private String telephone;

    private String adresseId;
}
