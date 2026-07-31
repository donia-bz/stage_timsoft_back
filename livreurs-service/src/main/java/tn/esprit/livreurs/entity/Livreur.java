package tn.esprit.livreurs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import tn.esprit.livreurs.entity.enums.StatutLivreur;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "livreurs")
public class Livreur {

    @Id
    private String id;
    private String nom;
    private String prenom;
    private StatutLivreur statut;
    private Double latitudeActuelle;
    private Double longitudeActuelle;
    private Double noteMoyenne;
    private String depotId;
}
