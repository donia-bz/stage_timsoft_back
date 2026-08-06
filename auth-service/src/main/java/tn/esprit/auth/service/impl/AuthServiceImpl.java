package tn.esprit.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.auth.dto.AuthResponse;
import tn.esprit.auth.dto.LoginRequest;
import tn.esprit.auth.dto.RegisterRequest;
import tn.esprit.auth.entity.Admin;
import tn.esprit.auth.entity.Client;
import tn.esprit.auth.entity.Livreur;
import tn.esprit.auth.entity.Utilisateur;
import tn.esprit.auth.entity.enums.StatutLivreur;
import tn.esprit.auth.repository.UtilisateurRepository;
import tn.esprit.auth.service.AuthService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur existe déjà avec cet email : " + request.getEmail());
        }

        String motDePasseHash = passwordEncoder.encode(request.getMotDePasse());
        Utilisateur utilisateur;
        String roleStr = request.getRole().toUpperCase();

        switch (roleStr) {
            case "CLIENT":
                utilisateur = Client.builder()
                        .nom(request.getNom())
                        .prenom(request.getPrenom())
                        .email(request.getEmail())
                        .motDePasseHash(motDePasseHash)
                        .telephone(request.getTelephone())
                        .role("CLIENT")
                        .dateCreation(LocalDateTime.now())
                        .statut("INSCRIPTION")
                        .approuve(false)
                        .entreprise(request.getEntreprise())
                        .matriculeFiscal(request.getMatriculeFiscal())
                        .build();
                break;
            case "LIVREUR":
                utilisateur = Livreur.builder()
                        .nom(request.getNom())
                        .prenom(request.getPrenom())
                        .email(request.getEmail())
                        .motDePasseHash(motDePasseHash)
                        .telephone(request.getTelephone())
                        .role("LIVREUR")
                        .dateCreation(LocalDateTime.now())
                        .statut("INSCRIPTION")
                        .approuve(false)
                        .latitudeActuelle(0.0f)
                        .longitudeActuelle(0.0f)
                        .noteMoyenne(5.0f)
                        .nombreLivraisons(0)
                        .dateInscription(LocalDateTime.now())
                        .build();
                break;
            case "ADMIN":
                utilisateur = Admin.builder()
                        .nom(request.getNom())
                        .prenom(request.getPrenom())
                        .email(request.getEmail())
                        .motDePasseHash(motDePasseHash)
                        .telephone(request.getTelephone())
                        .role("ADMIN")
                        .dateCreation(LocalDateTime.now())
                        .niveauAcces(request.getNiveauAcces() != null ? request.getNiveauAcces() : "MODERATEUR")
                        .build();
                break;
            default:
                throw new IllegalArgumentException("Rôle inconnu : " + request.getRole());
        }

        Utilisateur sauvegarde = utilisateurRepository.save(utilisateur);

        return AuthResponse.builder()
                .id(sauvegarde.getId())
                .nom(sauvegarde.getNom())
                .prenom(sauvegarde.getPrenom())
                .email(sauvegarde.getEmail())
                .role(sauvegarde.getRole())
                .token("mock-jwt-token-for-" + sauvegarde.getEmail())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Identifiants incorrects"));

        if (!passwordEncoder.matches(request.getMotDePasse(), utilisateur.getMotDePasseHash())) {
            throw new IllegalArgumentException("Identifiants incorrects");
        }



        return AuthResponse.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .role(utilisateur.getRole())
                .token("mock-jwt-token-for-" + utilisateur.getEmail())
                .build();
    }

    @Override
    public Utilisateur getProfile(String id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec l'id : " + id));
    }

    @Override
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    @Override
    public List<Utilisateur> getUsersByRole(String role) {
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    @Override
    public Utilisateur approuverUtilisateur(String id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec l'id : " + id));
        utilisateur.setApprouve(true);
        utilisateur.setStatut("ACTIF");
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void supprimerUtilisateur(String id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public Utilisateur changerStatut(String id, String statut) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec l'id : " + id));
        utilisateur.setStatut(statut);
        return utilisateurRepository.save(utilisateur);
    }
}
