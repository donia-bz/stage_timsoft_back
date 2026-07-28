package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "paiements")
public class Paiement {

    @Id
    private String id;

    private String commandeId;
    private Double montant;
    private String methode;   // CASH, CARTE
    private String statut;    // EN_ATTENTE, PAYE, ECHOUE
}
