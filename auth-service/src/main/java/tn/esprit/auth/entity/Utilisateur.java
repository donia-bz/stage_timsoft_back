package tn.esprit.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "utilisateurs")
public class Utilisateur {

    @Id
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;

    @CreatedDate
    private LocalDateTime dateCreation;
    private String role; // CLIENT, LIVREUR, ADMIN
}
