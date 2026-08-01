package tn.esprit.commandes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.commandes.entity.Enlevement;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.repository.EnlevementRepository;
import tn.esprit.commandes.service.EnlevementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnlevementServiceImpl implements EnlevementService {

    private final EnlevementRepository enlevementRepository;

    @Override
    public Enlevement createEnlevement(Enlevement enlevement) {
        return enlevementRepository.save(enlevement);
    }

    @Override
    public Enlevement getEnlevementById(String id) {
        return enlevementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enlevement introuvable avec l'id : " + id));
    }

    @Override
    public List<Enlevement> getEnlevementsByClient(String clientId) {
        return enlevementRepository.findByClientId(clientId);
    }

    @Override
    public List<Enlevement> getEnlevementsByLivreur(String livreurId) {
        return enlevementRepository.findByLivreurId(livreurId);
    }

    @Override
    public List<Enlevement> getAllEnlevements() {
        return enlevementRepository.findAll();
    }

    @Override
    public Enlevement updateEnlevement(String id, Enlevement enlevement) {
        Enlevement existing = getEnlevementById(id);
        if (enlevement.getClientId() != null) {
            existing.setClientId(enlevement.getClientId());
        }
        if (enlevement.getManifesteId() != null) {
            existing.setManifesteId(enlevement.getManifesteId());
        }
        if (enlevement.getLivreurId() != null) {
            existing.setLivreurId(enlevement.getLivreurId());
        }
        if (enlevement.getDateDemandee() != null) {
            existing.setDateDemandee(enlevement.getDateDemandee());
        }
        if (enlevement.getDateReelle() != null) {
            existing.setDateReelle(enlevement.getDateReelle());
        }
        if (enlevement.getStatut() != null) {
            existing.setStatut(enlevement.getStatut());
        }
        if (enlevement.getAdresseEnlevementId() != null) {
            existing.setAdresseEnlevementId(enlevement.getAdresseEnlevementId());
        }
        return enlevementRepository.save(existing);
    }

    @Override
    public void deleteEnlevement(String id) {
        Enlevement enlevement = getEnlevementById(id);
        enlevementRepository.delete(enlevement);
    }
}
