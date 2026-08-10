package tn.esprit.commandes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.commandes.dto.response.ColisResponse;
import tn.esprit.commandes.entity.Colis;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.repository.ColisRepository;
import tn.esprit.commandes.service.ColisService;
import tn.esprit.commandes.service.StatutTransitionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColisServiceImpl implements ColisService {

    private final ColisRepository colisRepository;

    @Override
    public List<ColisResponse> getColisByCommande(String commandeId) {
        return colisRepository.findByCommandeId(commandeId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ColisResponse> getColisByClient(String clientId) {
        return colisRepository.findByClientId(clientId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ColisResponse getColisById(String id) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis introuvable avec l'id : " + id));
        return toResponse(colis);
    }

    @Override
    public List<ColisResponse> searchColis(String query) {
        // Recherche par ID, ou par clientId si query ne correspond pas à un ID MongoDB
        List<Colis> results;
        try {
            // Essayer de traiter comme un ID MongoDB
            results = colisRepository.findById(query)
                    .map(List::of)
                    .orElseGet(() -> colisRepository.findByClientId(query));
        } catch (Exception e) {
            // Si ce n'est pas un ID valide, rechercher par clientId
            results = colisRepository.findByClientId(query);
        }
        return results.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ColisResponse updateStatut(String id, StatutCommande nouveauStatut) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis introuvable avec l'id : " + id));

        StatutCommande ancienStatut = colis.getStatut();

        // Validation de la transition selon les règles métier
        StatutTransitionService.validerTransition(ancienStatut, nouveauStatut);

        colis.setStatut(nouveauStatut);
        Colis updated = colisRepository.save(colis);
        return toResponse(updated);
    }

    private ColisResponse toResponse(Colis c) {
        return ColisResponse.builder()
                .id(c.getId())
                .commandeId(c.getCommandeId())
                .clientId(c.getClientId())
                .destinataireId(c.getDestinataireId())
                .depotId(c.getDepotId())
                .poids(c.getPoids())
                .dimensions(c.getDimensions())
                .fragile(c.getFragile())
                .statut(c.getStatut())
                .build();
    }
}
