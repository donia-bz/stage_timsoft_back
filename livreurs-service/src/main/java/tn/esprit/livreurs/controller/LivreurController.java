package tn.esprit.livreurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.livreurs.entity.AffectationVehicule;
import tn.esprit.livreurs.entity.Depot;
import tn.esprit.livreurs.entity.Livreur;
import tn.esprit.livreurs.entity.Vehicule;
import tn.esprit.livreurs.entity.enums.StatutLivreur;
import tn.esprit.livreurs.service.LivreurService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LivreurController {

    private final LivreurService livreurService;

    // --- Livreur endpoints ---

    @PostMapping("/livreurs")
    public ResponseEntity<Livreur> createLivreur(@RequestBody Livreur livreur) {
        Livreur saved = livreurService.saveLivreur(livreur);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/livreurs/{id}")
    public ResponseEntity<Livreur> getLivreurById(@PathVariable String id) {
        return ResponseEntity.ok(livreurService.getLivreurById(id));
    }

    @GetMapping("/livreurs")
    public ResponseEntity<List<Livreur>> getAllLivreurs() {
        return ResponseEntity.ok(livreurService.getAllLivreurs());
    }

    @GetMapping("/livreurs/disponibles")
    public ResponseEntity<List<Livreur>> getLivreursDisponibles() {
        return ResponseEntity.ok(livreurService.getLivreursDisponibles());
    }

    @PatchMapping("/livreurs/{id}/statut")
    public ResponseEntity<Livreur> updateStatut(@PathVariable String id, @RequestParam StatutLivreur statut) {
        return ResponseEntity.ok(livreurService.updateStatut(id, statut));
    }

    @PatchMapping("/livreurs/{id}/position")
    public ResponseEntity<Livreur> updatePosition(
            @PathVariable String id,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        return ResponseEntity.ok(livreurService.updatePosition(id, latitude, longitude));
    }

    @PatchMapping("/livreurs/{id}/depot")
    public ResponseEntity<Livreur> assignerDepot(@PathVariable String id, @RequestParam String depotId) {
        return ResponseEntity.ok(livreurService.assignerDepot(id, depotId));
    }

    @GetMapping("/livreurs/depot/{depotId}")
    public ResponseEntity<List<Livreur>> getLivreursByDepot(@PathVariable String depotId) {
        return ResponseEntity.ok(livreurService.getLivreursByDepot(depotId));
    }

    @PatchMapping("/livreurs/{id}/gouvernorat")
    public ResponseEntity<Livreur> assignerGouvernorat(@PathVariable String id, @RequestParam String gouvernorat) {
        return ResponseEntity.ok(livreurService.assignerGouvernorat(id, gouvernorat));
    }

    @GetMapping("/livreurs/gouvernorat/{gouvernorat}")
    public ResponseEntity<List<Livreur>> getLivreursByGouvernorat(@PathVariable String gouvernorat) {
        return ResponseEntity.ok(livreurService.getLivreursByGouvernorat(gouvernorat));
    }

    @GetMapping("/livreurs/{id}/livraisons")
    public ResponseEntity<List<?>> getLivraisonsByLivreur(@PathVariable String id) {
        return ResponseEntity.ok(livreurService.getLivraisonsByLivreur(id));
    }

    // --- AffectationVehicule endpoints ---

    @PostMapping("/livreurs/{livreurId}/vehicule")
    public ResponseEntity<AffectationVehicule> affecterVehicule(
            @PathVariable String livreurId,
            @RequestParam String vehiculeId) {
        AffectationVehicule affectation = livreurService.affecterVehicule(livreurId, vehiculeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(affectation);
    }

    @DeleteMapping("/livreurs/{livreurId}/vehicule")
    public ResponseEntity<AffectationVehicule> desaffecterVehicule(@PathVariable String livreurId) {
        return ResponseEntity.ok(livreurService.desaffecterVehicule(livreurId));
    }

    @GetMapping("/livreurs/{livreurId}/affectations")
    public ResponseEntity<List<AffectationVehicule>> getHistoriqueAffectationsLivreur(@PathVariable String livreurId) {
        return ResponseEntity.ok(livreurService.getHistoriqueAffectationsLivreur(livreurId));
    }

    // --- Vehicule endpoints ---

    @PostMapping("/vehicules")
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
        Vehicule saved = livreurService.saveVehicule(vehicule);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/vehicules/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable String id) {
        return ResponseEntity.ok(livreurService.getVehiculeById(id));
    }

    @GetMapping("/vehicules/livreur/{livreurId}")
    public ResponseEntity<Vehicule> getVehiculeActifByLivreurId(@PathVariable String livreurId) {
        return ResponseEntity.ok(livreurService.getVehiculeActifByLivreurId(livreurId));
    }

    @GetMapping("/vehicules/{vehiculeId}/affectations")
    public ResponseEntity<List<AffectationVehicule>> getHistoriqueAffectationsVehicule(@PathVariable String vehiculeId) {
        return ResponseEntity.ok(livreurService.getHistoriqueAffectationsVehicule(vehiculeId));
    }

    // --- Depot endpoints ---

    @PostMapping("/depots")
    public ResponseEntity<Depot> createDepot(@RequestBody Depot depot) {
        Depot saved = livreurService.saveDepot(depot);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/depots/{id}")
    public ResponseEntity<Depot> getDepotById(@PathVariable String id) {
        return ResponseEntity.ok(livreurService.getDepotById(id));
    }

    @GetMapping("/depots")
    public ResponseEntity<List<Depot>> getAllDepots() {
        return ResponseEntity.ok(livreurService.getAllDepots());
    }
}
