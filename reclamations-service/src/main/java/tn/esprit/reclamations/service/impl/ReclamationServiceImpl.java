package tn.esprit.reclamations.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.reclamations.entity.Reclamation;
import tn.esprit.reclamations.exception.ResourceNotFoundException;
import tn.esprit.reclamations.repository.ReclamationRepository;
import tn.esprit.reclamations.service.ReclamationService;
import tn.esprit.reclamations.websocket.ReclamationWebSocketHandler;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReclamationServiceImpl implements ReclamationService {

    private final ReclamationRepository reclamationRepository;
    private final ReclamationWebSocketHandler webSocketHandler;

    @Override
    public Reclamation createReclamation(Reclamation reclamation) {
        reclamation.setStatut("EN_ATTENTE");
        Reclamation saved = reclamationRepository.save(reclamation);
        System.out.println("✅ Réclamation créée: " + saved.getId() + " pour client: " + saved.getClientId());
        return saved;
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

        // Gérer la réponse admin visible par le client
        if (reclamation.getReponseAdmin() != null && !reclamation.getReponseAdmin().isEmpty()) {
            existingReclamation.setReponseAdmin(reclamation.getReponseAdmin());
            existingReclamation.setDateReponse(LocalDateTime.now());
            existingReclamation.setStatut("EN_COURS");
            System.out.println("💬 Réponse admin ajoutée pour réclamation " + id);

            // Notifier le client en temps réel via WebSocket
            webSocketHandler.notifyClient(existingReclamation.getClientId(),
                "{\"type\":\"NEW_RESPONSE\",\"reclamationId\":\"" + id + "\",\"reponse\":\"" + reclamation.getReponseAdmin() + "\"}");
        }

        Reclamation saved = reclamationRepository.save(existingReclamation);
        System.out.println("✅ Réclamation mise à jour: " + id);
        return saved;
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
        Reclamation saved = reclamationRepository.save(reclamation);
        System.out.println("✅ Statut mis à jour: " + id + " -> " + statut);

        // Notifier le client du changement de statut
        webSocketHandler.notifyClient(reclamation.getClientId(),
            "{\"type\":\"STATUS_UPDATE\",\"reclamationId\":\"" + id + "\",\"statut\":\"" + statut + "\"}");

        return saved;
    }

    @Override
    public List<Reclamation> getReclamationsByClient(String clientId) {
        List<Reclamation> reclamations = reclamationRepository.findByClientId(clientId);
        System.out.println("📝 Réclamations récupérées pour client " + clientId + ": " + reclamations.size());
        return reclamations;
    }

    @Override
    public List<Reclamation> getReclamationsByStatut(String statut) {
        return reclamationRepository.findByStatut(statut);
    }
}