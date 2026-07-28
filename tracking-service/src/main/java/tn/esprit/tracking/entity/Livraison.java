package tn.esprit.tracking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "livraisons")
public class Livraison {

    @Id
    private String id;
    private String commandeId;
    private String livreurId;
    private String statut; // affectee, en_cours, livree
    private LocalDateTime dateAffectation;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Double distanceKm;
}
