package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.commandes.entity.enums.StatutCommande;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "commandes")
public class Commande {

    @Id
    private String id;

    private String clientId;

    private Adresse adresseDepart;
    private Adresse adresseArrivee;

    private StatutCommande statut;
    private String typeService;

    @CreatedDate
    private LocalDateTime dateCreation;

    private Integer delaiEstimeMin;
    private Double montantTotal;
}
