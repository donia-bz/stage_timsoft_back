package tn.esprit.ia.service;

import tn.esprit.ia.dto.LivreurDTO;
import tn.esprit.ia.entity.AffectationIA;
import tn.esprit.ia.entity.PredictionDelai;

import java.util.List;

public interface IAService {
    PredictionDelai predirDelai(String commandeId, Double latDepart, Double longDepart, Double latArrivee, Double longArrivee, String typeService);
    AffectationIA calculerAffectation(String commandeId, Double latDepart, Double longDepart, List<LivreurDTO> livreurs);
    List<PredictionDelai> getPredictionsByCommande(String commandeId);
    List<AffectationIA> getAffectationsByCommande(String commandeId);
    tn.esprit.ia.dto.DispatchResponse dispatchGlobal(tn.esprit.ia.dto.DispatchRequest request);
}
