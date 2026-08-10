package tn.esprit.commandes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.commandes.dto.response.ColisResponse;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.service.ColisService;

import java.util.List;

@RestController
@RequestMapping("/api/colis")
@RequiredArgsConstructor
public class ColisController {

    private final ColisService colisService;

    @GetMapping("/{id}")
    public ResponseEntity<ColisResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(colisService.getColisById(id));
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<ColisResponse>> getByCommande(@PathVariable String commandeId) {
        return ResponseEntity.ok(colisService.getColisByCommande(commandeId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ColisResponse>> getByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(colisService.getColisByClient(clientId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ColisResponse>> search(@RequestParam String q) {
        return ResponseEntity.ok(colisService.searchColis(q));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<ColisResponse> updateStatut(
            @PathVariable String id,
            @RequestParam StatutCommande statut) {
        return ResponseEntity.ok(colisService.updateStatut(id, statut));
    }
}
