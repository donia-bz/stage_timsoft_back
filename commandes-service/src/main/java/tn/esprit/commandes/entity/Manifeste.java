package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.commandes.entity.enums.StatutManifeste;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "manifestes")
public class Manifeste {

    @Id
    private String id;
    private String clientId;

    @CreatedDate
    private LocalDateTime dateCreation;
    private Integer nombreColis;
    private StatutManifeste statut;
    private List<String> colisIds; // IDs des colis liés au manifeste
    private List<String> commandeIds; // IDs des commandes (pour faciliter la création)
}
