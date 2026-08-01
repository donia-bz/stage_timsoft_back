package tn.esprit.commandes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.commandes.entity.Destinataire;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.repository.DestinataireRepository;
import tn.esprit.commandes.service.DestinataireService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinataireServiceImpl implements DestinataireService {

    private final DestinataireRepository destinataireRepository;

    @Override
    public Destinataire createDestinataire(Destinataire destinataire) {
        return destinataireRepository.save(destinataire);
    }

    @Override
    public Destinataire getDestinataireById(String id) {
        return destinataireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destinataire introuvable avec l'id : " + id));
    }

    @Override
    public List<Destinataire> getAllDestinataires() {
        return destinataireRepository.findAll();
    }

    @Override
    public Destinataire updateDestinataire(String id, Destinataire destinataire) {
        Destinataire existing = getDestinataireById(id);
        existing.setNom(destinataire.getNom());
        existing.setTelephone(destinataire.getTelephone());
        existing.setAdresseId(destinataire.getAdresseId());
        return destinataireRepository.save(existing);
    }

    @Override
    public void deleteDestinataire(String id) {
        Destinataire destinataire = getDestinataireById(id);
        destinataireRepository.delete(destinataire);
    }
}
