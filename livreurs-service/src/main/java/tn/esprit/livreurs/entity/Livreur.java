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
@Document(collection = "livreurs")
public class Livreur {

    @Id
    private String id; // ID correspondant a l'Utilisateur de role LIVREUR dans auth-service
    private String nom;
    private String prenom;
    private String statut; // disponible, en_course, hors_ligne
    private Double latitudeActuelle;
    private Double longitudeActuelle;
    private String vehiculeId;
    private Double noteMoyenne;
}
