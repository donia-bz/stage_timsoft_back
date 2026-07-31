package tn.esprit.auth.dto;

import lombok.Data;

@Data
public class AdresseRequest {
    private String rue;
    private String ville;
    private String codePostal;
    private Double latitude;
    private Double longitude;
    private Boolean adressePrincipale;
}
