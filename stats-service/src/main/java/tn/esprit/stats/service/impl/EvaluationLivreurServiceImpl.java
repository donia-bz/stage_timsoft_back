package tn.esprit.stats.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.stats.entity.EvaluationLivreur;
import tn.esprit.stats.exception.ResourceNotFoundException;
import tn.esprit.stats.repository.EvaluationLivreurRepository;
import tn.esprit.stats.service.EvaluationLivreurService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationLivreurServiceImpl implements EvaluationLivreurService {

    private final EvaluationLivreurRepository evaluationRepository;

    @Override
    public EvaluationLivreur createEvaluation(EvaluationLivreur evaluation) {
        if (evaluation.getNote() < 1 || evaluation.getNote() > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }
        return evaluationRepository.save(evaluation);
    }

    @Override
    public EvaluationLivreur getEvaluationById(String id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Évaluation non trouvée avec l'ID: " + id));
    }

    @Override
    public List<EvaluationLivreur> getEvaluationsByLivreur(String livreurId) {
        return evaluationRepository.findByLivreurId(livreurId);
    }

    @Override
    public List<EvaluationLivreur> getEvaluationsByClient(String clientId) {
        return evaluationRepository.findByClientId(clientId);
    }

    @Override
    public List<EvaluationLivreur> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public Double getMoyenneNoteLivreur(String livreurId) {
        List<EvaluationLivreur> evaluations = evaluationRepository.findByLivreurId(livreurId);
        if (evaluations.isEmpty()) {
            return 0.0;
        }
        return evaluations.stream()
                .mapToInt(EvaluationLivreur::getNote)
                .average()
                .orElse(0.0);
    }
}