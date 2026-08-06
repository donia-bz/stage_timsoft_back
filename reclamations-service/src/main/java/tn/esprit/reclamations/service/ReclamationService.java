package tn.esprit.reclamations.service;

import tn.esprit.reclamations.entity.Reclamation;

import java.util.List;

public interface ReclamationService {
    Reclamation createReclamation(Reclamation reclamation);
    Reclamation getReclamationById(String id);
    List<Reclamation> getAllReclamations();
    Reclamation updateReclamation(String id, Reclamation reclamation);
    void deleteReclamation(String id);
    Reclamation updateStatut(String id, String statut);
    List<Reclamation> getReclamationsByClient(String clientId);
    List<Reclamation> getReclamationsByStatut(String statut);
}