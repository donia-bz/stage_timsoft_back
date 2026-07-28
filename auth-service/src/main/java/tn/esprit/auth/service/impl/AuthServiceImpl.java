package tn.esprit.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.auth.dto.AuthResponse;
import tn.esprit.auth.dto.LoginRequest;
import tn.esprit.auth.dto.RegisterRequest;
import tn.esprit.auth.entity.Admin;
import tn.esprit.auth.entity.Client;
import tn.esprit.auth.entity.Livreur;
import tn.esprit.auth.entity.Utilisateur;
import tn.esprit.auth.repository.UtilisateurRepository;
import tn.esprit.auth.service.AuthService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur existe déjà avec cet email : " + request.getEmail());
        }

        Utilisateur utilisateur;
        String roleStr = request.getRole().toUpperCase();

        switch (roleStr) {
            case "CLIENT":
                utilisateur = Client.builder()
                        .nom(request.getNom())
                        .prenom(request.getPrenom())
                        .email(request.getEmail())
                        .motDePasse(request.getMotDePasse())
                        .telephone(request.getTelephone())
                        .role("CLIENT")
                        .dateCreation(LocalDateTime.now())
                        .entreprise(request.getEntreprise())
                        .adresseDefautId(request.getAdresseDefautId())
                        .approuve(false)
                        .build();
                break;
            case "LIVREUR":
                utilisateur = Livreur.builder()
                        .nom(request.getNom())
                        .prenom(request.getPrenom())
                        .email(request.getEmail())
                        .motDePasse(request.getMotDePasse())
                        .telephone(request.getTelephone())
                        .role("LIVREUR")
                        .dateCreation(LocalDateTime.now())
                        .statut("disponible")
                        .latitudeActuelle(0.0)
                        .longitudeActuelle(0.0)
                        .noteMoyenne(5.0)
                        .approuve(false)
                        .build();
                break;
            case "ADMIN":
                utilisateur = Admin.builder()
                        .nom(request.getNom())
                        .prenom(request.getPrenom())
                        .email(request.getEmail())
                        .motDePasse(request.getMotDePasse())
                        .telephone(request.getTelephone())
                        .role("ADMIN")
                        .dateCreation(LocalDateTime.now())
                        .niveauAcces(request.getNiveauAcces() != null ? request.getNiveauAcces() : "MODERATEUR")
                        .approuve(true)
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

        if (!utilisateur.getMotDePasse().equals(request.getMotDePasse())) {
            throw new IllegalArgumentException("Identifiants incorrects");
        }

        if (utilisateur.getApprouve() != null && !utilisateur.getApprouve()) {
            throw new IllegalArgumentException("Votre compte est en attente d'approbation par l'administrateur.");
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
        Utilisateur u = utilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec l'id : " + id));
        u.setApprouve(true);
        Utilisateur saved = utilisateurRepository.save(u);
        System.out.println("Notification SMS/Mail envoyée à " + u.getTelephone() + " / " + u.getEmail() + " : Compte activé. Identifiants: " + u.getEmail() + " / " + u.getMotDePasse());
        return saved;
    }
}
