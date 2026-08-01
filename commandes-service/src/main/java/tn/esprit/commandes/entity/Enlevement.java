package tn.esprit.commandes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.commandes.entity.enums.StatutEnlevement;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "enlevements")
public class Enlevement {

    @Id
    private String id;
    private String clientId;
    private String manifesteId;
    private String livreurId;

    private LocalDateTime dateDemandee;
    private LocalDateTime dateReelle;
    private StatutEnlevement statut;
    private String adresseEnlevementId;
}
