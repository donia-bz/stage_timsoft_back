package tn.esprit.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Livreur extends Utilisateur {
    private String statut; // disponible, en_course, hors_ligne
    private Double latitudeActuelle;
    private Double longitudeActuelle;
    private String vehiculeId;
    private Double noteMoyenne;
}
