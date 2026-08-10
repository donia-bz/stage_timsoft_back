package tn.esprit.commandes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.commandes.dto.request.ManifesteRequest;
import tn.esprit.commandes.entity.Manifeste;
import tn.esprit.commandes.entity.enums.StatutManifeste;
import tn.esprit.commandes.service.ManifesteService;

import java.util.List;

@RestController
@RequestMapping("/api/manifestes")
@RequiredArgsConstructor
public class ManifesteController {

    private final ManifesteService manifesteService;

    @PostMapping
    public ResponseEntity<Manifeste> create(@RequestBody ManifesteRequest request) {
        Manifeste manifeste = Manifeste.builder()
                .clientId(request.getClientId())
                .nombreColis(request.getNombreColis())
                .statut(StatutManifeste.BROUILLON)
                .colisIds(request.getColisIds())
                .commandeIds(request.getCommandeIds())
                .build();
        return ResponseEntity.ok(manifesteService.createManifeste(manifeste));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manifeste> getById(@PathVariable String id) {
        return ResponseEntity.ok(manifesteService.getManifesteById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Manifeste>> getByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(manifesteService.getManifestesByClient(clientId));
    }

    @GetMapping("/client/{clientId}/brouillon")
    public ResponseEntity<Manifeste> getBrouillonByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(manifesteService.getBrouillonByClient(clientId));
    }

    @GetMapping
    public ResponseEntity<List<Manifeste>> getAll() {
        return ResponseEntity.ok(manifesteService.getAllManifestes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manifeste> update(@PathVariable String id, @RequestBody ManifesteRequest request) {
        Manifeste existing = manifesteService.getManifesteById(id);
        if (request.getClientId() != null) {
            existing.setClientId(request.getClientId());
        }
        if (request.getNombreColis() != null) {
            existing.setNombreColis(request.getNombreColis());
        }
        if (request.getColisIds() != null) {
            existing.setColisIds(request.getColisIds());
        }
        if (request.getCommandeIds() != null) {
            existing.setCommandeIds(request.getCommandeIds());
        }
        return ResponseEntity.ok(manifesteService.updateManifeste(id, existing));
    }

    @PatchMapping("/{id}/valider")
    public ResponseEntity<Manifeste> valider(@PathVariable String id) {
        return ResponseEntity.ok(manifesteService.validerManifeste(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        manifesteService.deleteManifeste(id);
        return ResponseEntity.noContent().build();
    }
}
