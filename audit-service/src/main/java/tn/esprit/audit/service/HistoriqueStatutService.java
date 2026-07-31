package tn.esprit.audit.service;

import tn.esprit.audit.dto.EvaluationRequest;
import tn.esprit.audit.dto.HistoriqueStatutRequest;
import tn.esprit.audit.entity.Evaluation;
import tn.esprit.audit.entity.HistoriqueStatut;

import java.util.List;

public interface HistoriqueStatutService {
    HistoriqueStatut enregistrer(HistoriqueStatutRequest request);
    List<HistoriqueStatut> getByEntite(String entiteType, String entiteId);
    List<HistoriqueStatut> getByAuteur(String auteurId);
}
