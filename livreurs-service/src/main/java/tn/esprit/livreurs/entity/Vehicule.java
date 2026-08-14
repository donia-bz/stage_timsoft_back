package tn.esprit.livreurs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicules")
public class Vehicule {

    @Id
    private String id;
    private String type; // CAMIONNETTE, UTILITAIRE, CAMION
    private String immatriculation;
    private String modele;
    private String marque;
    private Integer annee;
    private Double capaciteKg;
    private Double capaciteVolume;
    private String statut; // DISPONIBLE, EN_SERVICE, EN_COURSE, MAINTENANCE, HORS_SERVICE
    private String livreurId;
    private String depotId;
}
