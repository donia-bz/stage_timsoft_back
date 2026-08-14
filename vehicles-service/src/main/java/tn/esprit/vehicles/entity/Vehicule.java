package tn.esprit.vehicles.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicules")
public class Vehicule {

    @Id
    private String id;
    private String immatriculation;
    private String modele;
    private String marque;
    private String type; // Voiture, Moto, Camionnette, UTILITAIRE, CAMION
    private Integer capacite;
    private Double capaciteKg;
    private Double capaciteVolume;
    private Integer annee;
    private String statut; // DISPONIBLE, EN_MAINTENANCE, HORS_SERVICE, MAINTENANCE, EN_SERVICE, EN_COURSE
    private String photoUrl;
    private String livreurId; // Affectation à un livreur

    @CreatedDate
    private LocalDateTime dateAjout;
}