package tn.esprit.audit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.audit.dto.EvaluationRequest;
import tn.esprit.audit.entity.Evaluation;
import tn.esprit.audit.service.EvaluationService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<Evaluation> creer(@Valid @RequestBody EvaluationRequest request) {
        Evaluation evaluation = evaluationService.creer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluation);
    }

    @GetMapping("/livraison/{livraisonId}")
    public ResponseEntity<List<Evaluation>> getByLivraison(@PathVariable String livraisonId) {
        return ResponseEntity.ok(evaluationService.getByLivraison(livraisonId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Evaluation>> getByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(evaluationService.getByClient(clientId));
    }
}
