package tn.esprit.depots.service;

import tn.esprit.depots.entity.Depot;

import java.util.List;

public interface DepotService {
    Depot createDepot(Depot depot);
    Depot getDepotById(String id);
    List<Depot> getAllDepots();
    Depot updateDepot(String id, Depot depot);
    void deleteDepot(String id);
    List<Depot> getDepotsByGouvernorat(String gouvernorat);
    Depot getCapaciteActuelle(String id);
}