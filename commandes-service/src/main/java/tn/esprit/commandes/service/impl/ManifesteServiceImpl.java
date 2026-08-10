package tn.esprit.commandes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.commandes.entity.Colis;
import tn.esprit.commandes.entity.Manifeste;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.entity.enums.StatutManifeste;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.repository.ColisRepository;
import tn.esprit.commandes.repository.CommandeRepository;
import tn.esprit.commandes.repository.ManifesteRepository;
import tn.esprit.commandes.service.ManifesteService;
import tn.esprit.commandes.entity.Commande;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManifesteServiceImpl implements ManifesteService {

    private final ManifesteRepository manifesteRepository;
    private final ColisRepository colisRepository;
    private final CommandeRepository commandeRepository;

    @Override
    public Manifeste createManifeste(Manifeste manifeste) {
        // Si des commandeIds sont fournis, les convertir en colisIds
        if (manifeste.getCommandeIds() != null && !manifeste.getCommandeIds().isEmpty()) {
            List<String> colisIds = colisRepository.findByCommandeIdIn(manifeste.getCommandeIds())
                    .stream()
                    .map(Colis::getId)
                    .toList();
            manifeste.setColisIds(colisIds);
        }
        return manifesteRepository.save(manifeste);
    }

    @Override
    public Manifeste getManifesteById(String id) {
        return manifesteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manifeste introuvable avec l'id : " + id));
    }

    @Override
    public List<Manifeste> getManifestesByClient(String clientId) {
        return manifesteRepository.findByClientId(clientId);
    }

    @Override
    public Manifeste getBrouillonByClient(String clientId) {
        return manifesteRepository.findByClientIdAndStatut(clientId, StatutManifeste.BROUILLON)
                .orElseGet(() -> {
                    // Créer un nouveau manifeste brouillon si aucun n'existe
                    Manifeste newManifeste = Manifeste.builder()
                            .clientId(clientId)
                            .nombreColis(0)
                            .statut(StatutManifeste.BROUILLON)
                            .colisIds(new java.util.ArrayList<>())
                            .build();
                    return manifesteRepository.save(newManifeste);
                });
    }

    @Override
    public List<Manifeste> getAllManifestes() {
        return manifesteRepository.findAll();
    }

    @Override
    public Manifeste updateManifeste(String id, Manifeste manifeste) {
        Manifeste existing = getManifesteById(id);
        if (manifeste.getClientId() != null) {
            existing.setClientId(manifeste.getClientId());
        }
        if (manifeste.getNombreColis() != null) {
            existing.setNombreColis(manifeste.getNombreColis());
        }
        if (manifeste.getStatut() != null) {
            existing.setStatut(manifeste.getStatut());
        }
        if (manifeste.getColisIds() != null) {
            existing.setColisIds(manifeste.getColisIds());
        }
        return manifesteRepository.save(existing);
    }

    @Override
    public Manifeste validerManifeste(String id) {
        Manifeste manifeste = getManifesteById(id);

        // Changer le statut du manifeste
        manifeste.setStatut(StatutManifeste.IMPRIME);

        // Mettre à jour les statuts des colis associés et des commandes : EN_ATTENTE/MANIFESTE → A_ENLEVER
        if (manifeste.getColisIds() != null && !manifeste.getColisIds().isEmpty()) {
            List<Colis> colisList = colisRepository.findAllById(manifeste.getColisIds());
            for (Colis colis : colisList) {
                // Seuls les colis en EN_ATTENTE ou MANIFESTE passent à A_ENLEVER
                if (colis.getStatut() == StatutCommande.EN_ATTENTE ||
                    colis.getStatut() == StatutCommande.MANIFESTE) {
                    colis.setStatut(StatutCommande.A_ENLEVER);
                    colisRepository.save(colis);
                    
                    if (colis.getCommandeId() != null) {
                        commandeRepository.findById(colis.getCommandeId()).ifPresent(cmd -> {
                            cmd.setStatut(StatutCommande.A_ENLEVER);
                            commandeRepository.save(cmd);
                        });
                    }
                }
            }
        }

        return manifesteRepository.save(manifeste);
    }

    @Override
    public void deleteManifeste(String id) {
        Manifeste manifeste = getManifesteById(id);
        manifesteRepository.delete(manifeste);
    }
}
