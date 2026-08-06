package tn.esprit.auth.service;

import tn.esprit.auth.dto.AuthResponse;
import tn.esprit.auth.dto.LoginRequest;
import tn.esprit.auth.dto.RegisterRequest;
import tn.esprit.auth.entity.Utilisateur;

import java.util.List;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    Utilisateur getProfile(String id);
    List<Utilisateur> getAllUsers();
    List<Utilisateur> getUsersByRole(String role);
    Utilisateur approuverUtilisateur(String id);
    void supprimerUtilisateur(String id);
    Utilisateur changerStatut(String id, String statut);
}
