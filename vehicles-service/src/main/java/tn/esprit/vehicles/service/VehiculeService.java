package tn.esprit.vehicles.service;

import tn.esprit.vehicles.entity.Vehicule;

import java.util.List;

public interface VehiculeService {
    Vehicule createVehicule(Vehicule vehicule);
    Vehicule getVehiculeById(String id);
    List<Vehicule> getAllVehicules();
    Vehicule updateVehicule(String id, Vehicule vehicule);
    void deleteVehicule(String id);
    Vehicule updateStatut(String id, String statut);
    Vehicule affecterLivreur(String id, String livreurId);
    List<Vehicule> getVehiculesDisponibles();
    List<Vehicule> getVehiculesByLivreur(String livreurId);
}