package tn.esprit.reclamations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.reclamations.entity.Reclamation;
import tn.esprit.reclamations.service.ReclamationService;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations")
@RequiredArgsConstructor
public class ReclamationController {

    private final ReclamationService reclamationService;

    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        return ResponseEntity.ok(reclamationService.getAllReclamations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable String id) {
        return ResponseEntity.ok(reclamationService.getReclamationById(id));
    }

    @PostMapping
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) {
        Reclamation createdReclamation = reclamationService.createReclamation(reclamation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReclamation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable String id, @RequestBody Reclamation reclamation) {
        return ResponseEntity.ok(reclamationService.updateReclamation(id, reclamation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable String id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<Reclamation> updateStatut(@PathVariable String id, @RequestParam String statut) {
        return ResponseEntity.ok(reclamationService.updateStatut(id, statut));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Reclamation>> getReclamationsByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(reclamationService.getReclamationsByClient(clientId));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Reclamation>> getReclamationsByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(reclamationService.getReclamationsByStatut(statut));
    }
}