package tn.esprit.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.auth.entity.Adresse;
import tn.esprit.auth.exception.ResourceNotFoundException;
import tn.esprit.auth.repository.AdresseRepository;
import tn.esprit.auth.service.AdresseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdresseServiceImpl implements AdresseService {

    private final AdresseRepository adresseRepository;

    @Override
    public Adresse createAdresse(Adresse adresse) {
        return adresseRepository.save(adresse);
    }

    @Override
    public Adresse getAdresseById(String id) {
        return adresseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adresse introuvable avec l'id : " + id));
    }

    @Override
    public List<Adresse> getAllAdresses() {
        return adresseRepository.findAll();
    }

    @Override
    public Adresse updateAdresse(String id, Adresse adresse) {
        Adresse existing = getAdresseById(id);
        existing.setRue(adresse.getRue());
        existing.setVille(adresse.getVille());
        existing.setCodePostal(adresse.getCodePostal());
        existing.setLatitude(adresse.getLatitude());
        existing.setLongitude(adresse.getLongitude());
        existing.setAdressePrincipale(adresse.getAdressePrincipale());
        return adresseRepository.save(existing);
    }

    @Override
    public void deleteAdresse(String id) {
        Adresse adresse = getAdresseById(id);
        adresseRepository.delete(adresse);
    }
}
