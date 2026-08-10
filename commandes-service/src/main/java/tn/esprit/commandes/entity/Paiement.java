package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.commandes.entity.enums.StatutPaiement;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "paiements")
public class Paiement {

    @Id
    private String id;

    private String commandeId;
    private String clientId;
    private Double montant;
    private Double frais;
    private Double net;
    private String methode;
    private StatutPaiement statut;

    @CreatedDate
    private LocalDateTime dateCreation;
}
