package tn.esprit.commandes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.commandes.entity.enums.StatutPaiement;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponse {
    private String id;
    private String commandeId;
    private Double montant;
    private String methode;
    private StatutPaiement statut;
}
