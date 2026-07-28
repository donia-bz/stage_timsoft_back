package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Adresse est un objet embarque (pas de collection MongoDB dediee).
 * Elle vit toujours a l'interieur d'une Commande (adresseDepart / adresseArrivee).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {
    private String rue;
    private String ville;
    private String codePostal;
    private Double latitude;
    private Double longitude;
}
