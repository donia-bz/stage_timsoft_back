package tn.esprit.commandes.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CommandeRequest {

    @NotBlank(message = "L'identifiant client est obligatoire")
    private String clientId;

    @NotBlank(message = "L'adresse de depart est obligatoire")
    private String adresseDepartId;

    @NotBlank(message = "L'adresse d'arrivee est obligatoire")
    private String adresseArriveeId;

    @NotNull(message = "Le type de service est obligatoire")
    private String typeService;

    @Valid
    private List<ColisRequest> colis;
}
