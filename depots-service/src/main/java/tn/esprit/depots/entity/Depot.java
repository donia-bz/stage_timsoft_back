package tn.esprit.depots.entity;

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
@Document(collection = "depots")
public class Depot {

    @Id
    private String id;
    private String nom;
    private String ville;
    private String gouvernorat;
    private Integer capacite;
    private String adresse;
    private Double latitude;
    private Double longitude;

    @CreatedDate
    private LocalDateTime dateCreation;
}