package tn.esprit.tracking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import tn.esprit.tracking.entity.enums.StatutLivraison;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "livraisons")
public class Livraison {

    @Id
    private String id;
    private String colisId;
    private String livreurId;
    private StatutLivraison statut;
    private LocalDateTime dateAffectation;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Double distanceKm;
}
