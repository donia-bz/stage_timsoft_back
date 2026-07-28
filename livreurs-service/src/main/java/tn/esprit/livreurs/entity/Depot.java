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
@Document(collection = "depots")
public class Depot {

    @Id
    private String id;
    private String nom;
    private String ville;
    private Adresse adresse; // Embedded Adresse
    private Integer capacite;
}
