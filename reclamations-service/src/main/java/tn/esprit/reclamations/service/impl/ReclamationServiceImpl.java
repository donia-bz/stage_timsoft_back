package tn.esprit.reclamations.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.reclamations.entity.Reclamation;
import tn.esprit.reclamations.exception.ResourceNotFoundException;
import tn.esprit.reclamations.repository.ReclamationRepository;
import tn.esprit.reclamations.service.ReclamationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReclamationServiceImpl implements ReclamationService {

    private final ReclamationRepository reclamationRepository;

    @Override
    public Reclamation createReclamation(Reclamation reclamation) {
        reclamation.setStatut("EN_ATTENTE");
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation getReclamationById(String id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réclamation non trouvée avec l'ID: " + id));
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation updateReclamation(String id, Reclamation reclamation) {
        Reclamation existingReclamation = getReclamationById(id);
        existingReclamation.setDescription(reclamation.getDescription());
        existingReclamation.setType(reclamation.getType());
        existingReclamation.setAdminCommentaire(reclamation.getAdminCommentaire());
        return reclamationRepository.save(existingReclamation);
    }

    @Override
    public void deleteReclamation(String id) {
        Reclamation reclamation = getReclamationById(id);
        reclamationRepository.delete(reclamation);
    }

    @Override
    public Reclamation updateStatut(String id, String statut) {
        Reclamation reclamation = getReclamationById(id);
        reclamation.setStatut(statut);
        if ("RESOLUE".equals(statut)) {
            reclamation.setDateResolution(LocalDateTime.now());
        }
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> getReclamationsByClient(String clientId) {
        return reclamationRepository.findByClientId(clientId);
    }

    @Override
    public List<Reclamation> getReclamationsByStatut(String statut) {
        return reclamationRepository.findByStatut(statut);
    }
}