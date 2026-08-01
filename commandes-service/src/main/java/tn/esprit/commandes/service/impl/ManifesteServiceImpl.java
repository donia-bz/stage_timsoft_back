package tn.esprit.commandes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.commandes.entity.Manifeste;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.repository.ManifesteRepository;
import tn.esprit.commandes.service.ManifesteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManifesteServiceImpl implements ManifesteService {

    private final ManifesteRepository manifesteRepository;

    @Override
    public Manifeste createManifeste(Manifeste manifeste) {
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
    public void deleteManifeste(String id) {
        Manifeste manifeste = getManifesteById(id);
        manifesteRepository.delete(manifeste);
    }
}
