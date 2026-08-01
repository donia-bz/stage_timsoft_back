package tn.esprit.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tn.esprit.auth.entity.enums.StatutLivreur;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Livreur extends Utilisateur {
    private StatutLivreur statut;
    private Float latitudeActuelle;
    private Float longitudeActuelle;
    private Float noteMoyenne;
}
