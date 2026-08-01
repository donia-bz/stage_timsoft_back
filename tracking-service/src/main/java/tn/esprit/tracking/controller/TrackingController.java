package tn.esprit.tracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tracking.entity.Livraison;
import tn.esprit.tracking.entity.PositionTracking;
import tn.esprit.tracking.service.TrackingService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    // --- Livraison endpoints ---

    @PostMapping("/livraisons")
    public ResponseEntity<Livraison> creer(@RequestParam String colisId, @RequestParam String livreurId) {
        Livraison livraison = trackingService.creerLivraison(colisId, livreurId);
        return ResponseEntity.status(HttpStatus.CREATED).body(livraison);
    }

    @PatchMapping("/livraisons/{id}/demarrer")
    public ResponseEntity<Livraison> demarrer(@PathVariable String id) {
        return ResponseEntity.ok(trackingService.demarrerLivraison(id));
    }

    @PatchMapping("/livraisons/{id}/terminer")
    public ResponseEntity<Livraison> terminer(@PathVariable String id) {
        return ResponseEntity.ok(trackingService.terminerLivraison(id));
    }

    @PatchMapping("/livraisons/{id}/echouer")
    public ResponseEntity<Livraison> echouer(@PathVariable String id) {
        return ResponseEntity.ok(trackingService.echouerLivraison(id));
    }

    @GetMapping("/livraisons/{id}")
    public ResponseEntity<Livraison> getById(@PathVariable String id) {
        return ResponseEntity.ok(trackingService.getLivraisonById(id));
    }

    @GetMapping("/livraisons")
    public ResponseEntity<List<Livraison>> getAll() {
        return ResponseEntity.ok(trackingService.getAllLivraisons());
    }

    @GetMapping("/livraisons/livreur/{livreurId}")
    public ResponseEntity<List<Livraison>> getByLivreur(@PathVariable String livreurId) {
        return ResponseEntity.ok(trackingService.getLivraisonsByLivreur(livreurId));
    }

    @GetMapping("/livraisons/colis/{colisId}")
    public ResponseEntity<List<Livraison>> getByColis(@PathVariable String colisId) {
        return ResponseEntity.ok(trackingService.getLivraisonsByColis(colisId));
    }

    // --- Position tracking endpoints ---

    @PostMapping("/tracking")
    public ResponseEntity<PositionTracking> ajouterPosition(
            @RequestParam String livraisonId,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        PositionTracking position = trackingService.ajouterPosition(livraisonId, latitude, longitude);
        return ResponseEntity.status(HttpStatus.CREATED).body(position);
    }

    @GetMapping("/tracking/livraison/{livraisonId}")
    public ResponseEntity<List<PositionTracking>> getPositions(@PathVariable String livraisonId) {
        return ResponseEntity.ok(trackingService.getPositions(livraisonId));
    }
}
