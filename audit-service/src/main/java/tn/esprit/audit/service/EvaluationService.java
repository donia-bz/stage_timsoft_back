package tn.esprit.audit.service;

import tn.esprit.audit.dto.EvaluationRequest;
import tn.esprit.audit.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    Evaluation creer(EvaluationRequest request);
    List<Evaluation> getByLivraison(String livraisonId);
    List<Evaluation> getByClient(String clientId);
}
