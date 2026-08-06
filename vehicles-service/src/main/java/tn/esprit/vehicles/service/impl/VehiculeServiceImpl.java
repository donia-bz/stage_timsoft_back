package tn.esprit.vehicles.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.vehicles.entity.Vehicule;
import tn.esprit.vehicles.exception.ResourceNotFoundException;
import tn.esprit.vehicles.repository.VehiculeRepository;
import tn.esprit.vehicles.service.VehiculeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public Vehicule createVehicule(Vehicule vehicule) {
        if (vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
            throw new IllegalArgumentException("Un véhicule avec cette immatriculation existe déjà");
        }
        vehicule.setStatut("DISPONIBLE");
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule getVehiculeById(String id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé avec l'ID: " + id));
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule updateVehicule(String id, Vehicule vehicule) {
        Vehicule existingVehicule = getVehiculeById(id);
        existingVehicule.setImmatriculation(vehicule.getImmatriculation());
        existingVehicule.setModele(vehicule.getModele());
        existingVehicule.setMarque(vehicule.getMarque());
        existingVehicule.setType(vehicule.getType());
        existingVehicule.setCapacite(vehicule.getCapacite());
        existingVehicule.setPhotoUrl(vehicule.getPhotoUrl());
        return vehiculeRepository.save(existingVehicule);
    }

    @Override
    public void deleteVehicule(String id) {
        Vehicule vehicule = getVehiculeById(id);
        vehiculeRepository.delete(vehicule);
    }

    @Override
    public Vehicule updateStatut(String id, String statut) {
        Vehicule vehicule = getVehiculeById(id);
        vehicule.setStatut(statut);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule affecterLivreur(String id, String livreurId) {
        Vehicule vehicule = getVehiculeById(id);
        vehicule.setLivreurId(livreurId);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> getVehiculesDisponibles() {
        return vehiculeRepository.findByStatut("DISPONIBLE");
    }

    @Override
    public List<Vehicule> getVehiculesByLivreur(String livreurId) {
        return vehiculeRepository.findByLivreurId(livreurId);
    }
}