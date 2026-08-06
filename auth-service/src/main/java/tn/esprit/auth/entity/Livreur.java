package tn.esprit.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tn.esprit.auth.entity.enums.StatutLivreur;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Livreur extends Utilisateur {
    private String statutLivreur; // String pour compatibilité avec parent
    private Float latitudeActuelle;
    private Float longitudeActuelle;
    private Float noteMoyenne;
    private Integer nombreLivraisons;
    private LocalDateTime dateInscription;
}
