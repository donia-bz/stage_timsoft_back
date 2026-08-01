package tn.esprit.auth.dto;

import lombok.Data;

@Data
public class AdresseRequestForController {
    private String rue;
    private String ville;
    private String codePostal;
    private Float latitude;
    private Float longitude;
    private Boolean adressePrincipale;
}
