package tn.esprit.commandes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.commandes.entity.Adresse;
import tn.esprit.commandes.entity.enums.StatutCommande;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandeResponse {
    private String id;
    private String clientId;
    private Adresse adresseDepart;
    private Adresse adresseArrivee;
    private StatutCommande statut;
    private String typeService;
    private LocalDateTime dateCreation;
    private Integer delaiEstimeMin;
    private Double montantTotal;
    private List<ColisResponse> colis;
}
