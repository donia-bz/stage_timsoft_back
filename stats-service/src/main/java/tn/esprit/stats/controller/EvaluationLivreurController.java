package tn.esprit.stats.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.stats.entity.EvaluationLivreur;
import tn.esprit.stats.service.EvaluationLivreurService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats/evaluations")
@RequiredArgsConstructor
public class EvaluationLivreurController {

    private final EvaluationLivreurService evaluationService;

    @PostMapping
    public ResponseEntity<EvaluationLivreur> createEvaluation(@RequestBody EvaluationLivreur evaluation) {
        EvaluationLivreur createdEvaluation = evaluationService.createEvaluation(evaluation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationLivreur> getEvaluationById(@PathVariable String id) {
        return ResponseEntity.ok(evaluationService.getEvaluationById(id));
    }

    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<EvaluationLivreur>> getEvaluationsByLivreur(@PathVariable String livreurId) {
        return ResponseEntity.ok(evaluationService.getEvaluationsByLivreur(livreurId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<EvaluationLivreur>> getEvaluationsByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(evaluationService.getEvaluationsByClient(clientId));
    }

    @GetMapping("/livreur/{livreurId}/moyenne")
    public ResponseEntity<Map<String, Double>> getMoyenneNoteLivreur(@PathVariable String livreurId) {
        Double moyenne = evaluationService.getMoyenneNoteLivreur(livreurId);
        return ResponseEntity.ok(Map.of("moyenne", moyenne));
    }

    @GetMapping
    public ResponseEntity<List<EvaluationLivreur>> getAllEvaluations() {
        return ResponseEntity.ok(evaluationService.getAllEvaluations());
    }
}