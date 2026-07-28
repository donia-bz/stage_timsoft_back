package tn.esprit.commandes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.commandes.dto.response.ColisResponse;
import tn.esprit.commandes.entity.Colis;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.repository.ColisRepository;
import tn.esprit.commandes.service.ColisService;

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
    public ColisResponse getColisById(String id) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis introuvable avec l'id : " + id));
        return toResponse(colis);
    }

    private ColisResponse toResponse(Colis c) {
        return ColisResponse.builder()
                .id(c.getId())
                .commandeId(c.getCommandeId())
                .poids(c.getPoids())
                .dimensions(c.getDimensions())
                .fragile(c.getFragile())
                .statut(c.getStatut())
                .build();
    }
}
