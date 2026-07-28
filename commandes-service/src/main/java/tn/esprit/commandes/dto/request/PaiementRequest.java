package tn.esprit.commandes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaiementRequest {

    @NotBlank(message = "L'identifiant de la commande est obligatoire")
    private String commandeId;

    @NotNull(message = "Le montant est obligatoire")
    private Double montant;

    @NotBlank(message = "La méthode de paiement est obligatoire (CASH, CARTE)")
    private String methode;
}
