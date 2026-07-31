package tn.esprit.audit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.audit.dto.EvaluationRequest;
import tn.esprit.audit.entity.Evaluation;
import tn.esprit.audit.repository.EvaluationRepository;
import tn.esprit.audit.service.EvaluationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Override
    public Evaluation creer(EvaluationRequest request) {
        Evaluation evaluation = Evaluation.builder()
                .note(request.getNote())
                .commentaire(request.getCommentaire())
                .type(request.getType())
                .livraisonId(request.getLivraisonId())
                .clientId(request.getClientId())
                .build();
        return evaluationRepository.save(evaluation);
    }

    @Override
    public List<Evaluation> getByLivraison(String livraisonId) {
        return evaluationRepository.findByLivraisonId(livraisonId);
    }

    @Override
    public List<Evaluation> getByClient(String clientId) {
        return evaluationRepository.findByClientId(clientId);
    }
}
