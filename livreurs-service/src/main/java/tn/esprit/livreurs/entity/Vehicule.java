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
    private String livreurId;
    private String type; // MOTO, VOITURE, VELO
    private String immatriculation;
    private Double capaciteChargeKg;
}
