package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Adresse est une entité autonome avec sa propre collection MongoDB.
 * Elle peut être référencée par Client, Expediteur, Depot, etc.
 * Adaptée pour le modèle First Delivery/Navex en Tunisie.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "adresses")
public class Adresse {
    @Id
    private String id;
    private String gouvernorat;
    private String ville;
    private String localite;
    private String rue;
    private String codePostal;
    private String telephone;
    private Double latitude;
    private Double longitude;
    private Boolean adressePrincipale;
}
