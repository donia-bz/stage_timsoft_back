package tn.esprit.livreurs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {
    private String id;
    private String rue;
    private String ville;
    private String codePostal;
    private Double latitude;
    private Double longitude;
    private Boolean adressePrincipale;
}
