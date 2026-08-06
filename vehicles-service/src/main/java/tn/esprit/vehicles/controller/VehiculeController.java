package tn.esprit.vehicles.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.vehicles.entity.Vehicule;
import tn.esprit.vehicles.service.VehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @GetMapping
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        return ResponseEntity.ok(vehiculeService.getAllVehicules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable String id) {
        return ResponseEntity.ok(vehiculeService.getVehiculeById(id));
    }

    @PostMapping
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
        Vehicule createdVehicule = vehiculeService.createVehicule(vehicule);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehicule(@PathVariable String id, @RequestBody Vehicule vehicule) {
        return ResponseEntity.ok(vehiculeService.updateVehicule(id, vehicule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable String id) {
        vehiculeService.deleteVehicule(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<Vehicule> updateStatut(@PathVariable String id, @RequestParam String statut) {
        return ResponseEntity.ok(vehiculeService.updateStatut(id, statut));
    }

    @PatchMapping("/{id}/affecter")
    public ResponseEntity<Vehicule> affecterLivreur(@PathVariable String id, @RequestParam String livreurId) {
        return ResponseEntity.ok(vehiculeService.affecterLivreur(id, livreurId));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Vehicule>> getVehiculesDisponibles() {
        return ResponseEntity.ok(vehiculeService.getVehiculesDisponibles());
    }

    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<Vehicule>> getVehiculesByLivreur(@PathVariable String livreurId) {
        return ResponseEntity.ok(vehiculeService.getVehiculesByLivreur(livreurId));
    }
}