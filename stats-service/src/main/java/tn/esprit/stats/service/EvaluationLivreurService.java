package tn.esprit.stats.service;

import tn.esprit.stats.entity.EvaluationLivreur;

import java.util.List;

public interface EvaluationLivreurService {
    EvaluationLivreur createEvaluation(EvaluationLivreur evaluation);
    EvaluationLivreur getEvaluationById(String id);
    List<EvaluationLivreur> getEvaluationsByLivreur(String livreurId);
    List<EvaluationLivreur> getEvaluationsByClient(String clientId);
    List<EvaluationLivreur> getAllEvaluations();
    Double getMoyenneNoteLivreur(String livreurId);
}