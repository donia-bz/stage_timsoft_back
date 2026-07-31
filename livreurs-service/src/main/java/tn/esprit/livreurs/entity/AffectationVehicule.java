package tn.esprit.livreurs.entity;

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
@Document(collection = "affectations_vehicule")
public class AffectationVehicule {

    @Id
    private String id;
    private String livreurId;
    private String vehiculeId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
}
