package tn.esprit.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String token;
}
