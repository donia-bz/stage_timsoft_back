package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.entity.enums.TypeService;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "commandes")
public class Commande {

    @Id
    private String id;

    private String clientId;

    private Adresse adresseDepart;   // embarque
    private Adresse adresseArrivee;  // embarque

    private StatutCommande statut;
    private TypeService typeService;

    @CreatedDate
    private LocalDateTime dateCreation;

    private Integer delaiEstimeMin;
    private Double montantTotal;

    private List<String> colisIds; // references vers la collection "colis"
}
