package tn.esprit.audit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.audit.dto.HistoriqueStatutRequest;
import tn.esprit.audit.entity.HistoriqueStatut;
import tn.esprit.audit.service.HistoriqueStatutService;

import java.util.List;

@RestController
@RequestMapping("/api/historique-statut")
@RequiredArgsConstructor
public class HistoriqueStatutController {

    private final HistoriqueStatutService historiqueStatutService;

    @PostMapping
    public ResponseEntity<HistoriqueStatut> enregistrer(@Valid @RequestBody HistoriqueStatutRequest request) {
        HistoriqueStatut historique = historiqueStatutService.enregistrer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(historique);
    }

    @GetMapping("/entite/{entiteType}/{entiteId}")
    public ResponseEntity<List<HistoriqueStatut>> getByEntite(
            @PathVariable String entiteType,
            @PathVariable String entiteId) {
        return ResponseEntity.ok(historiqueStatutService.getByEntite(entiteType, entiteId));
    }

    @GetMapping("/auteur/{auteurId}")
    public ResponseEntity<List<HistoriqueStatut>> getByAuteur(@PathVariable String auteurId) {
        return ResponseEntity.ok(historiqueStatutService.getByAuteur(auteurId));
    }
}
