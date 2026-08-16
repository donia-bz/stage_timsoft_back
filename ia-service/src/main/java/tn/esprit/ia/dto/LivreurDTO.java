package tn.esprit.ia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivreurDTO {
    private String id;
    private String nom;
    private String prenom;
    private Double latitudeActuelle;
    private Double longitudeActuelle;
    private Double noteMoyenne;
    private String gouvernorat;
}
