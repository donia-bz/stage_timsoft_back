package tn.esprit.commandes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.commandes.entity.enums.StatutPaiement;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponse {
    private String id;
    private String commandeId;
    private String clientId;
    private Double montant;
    private Double frais;
    private Double net;
    private String methode;
    private StatutPaiement statut;
    private LocalDateTime dateCreation;
}
