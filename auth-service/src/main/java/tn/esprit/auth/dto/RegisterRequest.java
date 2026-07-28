package tn.esprit.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prenom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit etre valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;

    private String telephone;

    @NotBlank(message = "Le role est obligatoire (CLIENT, LIVREUR, ADMIN)")
    private String role;

    // Specifique Client
    private String entreprise;
    private String adresseDefautId;

    // Specifique Admin
    private String niveauAcces;
}
