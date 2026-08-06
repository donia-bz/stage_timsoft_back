package tn.esprit.depots.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.depots.entity.Depot;
import tn.esprit.depots.service.DepotService;

import java.util.List;

@RestController
@RequestMapping("/api/depots")
@RequiredArgsConstructor
public class DepotController {

    private final DepotService depotService;

    @GetMapping
    public ResponseEntity<List<Depot>> getAllDepots() {
        return ResponseEntity.ok(depotService.getAllDepots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depot> getDepotById(@PathVariable String id) {
        return ResponseEntity.ok(depotService.getDepotById(id));
    }

    @PostMapping
    public ResponseEntity<Depot> createDepot(@RequestBody Depot depot) {
        Depot createdDepot = depotService.createDepot(depot);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Depot> updateDepot(@PathVariable String id, @RequestBody Depot depot) {
        return ResponseEntity.ok(depotService.updateDepot(id, depot));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepot(@PathVariable String id) {
        depotService.deleteDepot(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gouvernorat/{gouvernorat}")
    public ResponseEntity<List<Depot>> getDepotsByGouvernorat(@PathVariable String gouvernorat) {
        return ResponseEntity.ok(depotService.getDepotsByGouvernorat(gouvernorat));
    }

    @GetMapping("/{id}/capacite")
    public ResponseEntity<Depot> getCapaciteActuelle(@PathVariable String id) {
        return ResponseEntity.ok(depotService.getCapaciteActuelle(id));
    }
}