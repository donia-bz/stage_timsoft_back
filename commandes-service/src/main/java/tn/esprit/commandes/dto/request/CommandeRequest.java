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

    // Adresses optionnelles pour simplifier la création de commandes
    private String adresseDepartId;
    private String adresseArriveeId;

    @NotNull(message = "Le type de service est obligatoire")
    private String typeService;

    private Double montantTotal;
    private String nomDestinataire;
    private String telephoneDestinataire;

    @Valid
    private List<ColisRequest> colis;
}
