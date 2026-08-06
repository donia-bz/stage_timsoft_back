package tn.esprit.depots.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.depots.entity.Depot;
import tn.esprit.depots.exception.ResourceNotFoundException;
import tn.esprit.depots.repository.DepotRepository;
import tn.esprit.depots.service.DepotService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepotServiceImpl implements DepotService {

    private final DepotRepository depotRepository;

    @Override
    public Depot createDepot(Depot depot) {
        if (depotRepository.existsByNom(depot.getNom())) {
            throw new IllegalArgumentException("Un dépôt avec ce nom existe déjà");
        }
        return depotRepository.save(depot);
    }

    @Override
    public Depot getDepotById(String id) {
        return depotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépôt non trouvé avec l'ID: " + id));
    }

    @Override
    public List<Depot> getAllDepots() {
        return depotRepository.findAll();
    }

    @Override
    public Depot updateDepot(String id, Depot depot) {
        Depot existingDepot = getDepotById(id);
        existingDepot.setNom(depot.getNom());
        existingDepot.setVille(depot.getVille());
        existingDepot.setGouvernorat(depot.getGouvernorat());
        existingDepot.setCapacite(depot.getCapacite());
        existingDepot.setAdresse(depot.getAdresse());
        existingDepot.setLatitude(depot.getLatitude());
        existingDepot.setLongitude(depot.getLongitude());
        return depotRepository.save(existingDepot);
    }

    @Override
    public void deleteDepot(String id) {
        Depot depot = getDepotById(id);
        depotRepository.delete(depot);
    }

    @Override
    public List<Depot> getDepotsByGouvernorat(String gouvernorat) {
        return depotRepository.findByGouvernorat(gouvernorat);
    }

    @Override
    public Depot getCapaciteActuelle(String id) {
        return getDepotById(id);
    }
}