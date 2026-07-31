package tn.esprit.commandes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.commandes.dto.request.PaiementRequest;
import tn.esprit.commandes.dto.response.PaiementResponse;
import tn.esprit.commandes.entity.enums.StatutPaiement;
import tn.esprit.commandes.service.PaiementService;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping
    public ResponseEntity<PaiementResponse> enregistrer(@Valid @RequestBody PaiementRequest request) {
        PaiementResponse response = paiementService.enregistrerPaiement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(paiementService.getPaiementById(id));
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<PaiementResponse> getByCommandeId(@PathVariable String commandeId) {
        return ResponseEntity.ok(paiementService.getPaiementByCommandeId(commandeId));
    }

    @GetMapping
    public ResponseEntity<List<PaiementResponse>> getAll() {
        return ResponseEntity.ok(paiementService.getAllPaiements());
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<PaiementResponse> updateStatut(
            @PathVariable String id,
            @RequestParam StatutPaiement statut) {
        return ResponseEntity.ok(paiementService.updateStatutPaiement(id, statut));
    }
}
