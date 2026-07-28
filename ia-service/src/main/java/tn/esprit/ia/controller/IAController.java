package tn.esprit.ia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ia.dto.LivreurDTO;
import tn.esprit.ia.entity.AffectationIA;
import tn.esprit.ia.entity.PredictionDelai;
import tn.esprit.ia.service.IAService;

import java.util.List;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
public class IAController {

    private final IAService iaService;

    @PostMapping("/predict-delai")
    public ResponseEntity<PredictionDelai> predictDelai(
            @RequestParam String commandeId,
            @RequestParam(required = false) Double latDepart,
            @RequestParam(required = false) Double longDepart,
            @RequestParam(required = false) Double latArrivee,
            @RequestParam(required = false) Double longArrivee,
            @RequestParam(defaultValue = "STANDARD") String typeService) {
        PredictionDelai prediction = iaService.predirDelai(commandeId, latDepart, longDepart, latArrivee, longArrivee, typeService);
        return ResponseEntity.status(HttpStatus.CREATED).body(prediction);
    }

    @PostMapping("/affecter-livreur")
    public ResponseEntity<AffectationIA> affecterLivreur(
            @RequestParam String colisId,
            @RequestParam(required = false) Double latColis,
            @RequestParam(required = false) Double longColis,
            @RequestBody List<LivreurDTO> livreurs) {
        AffectationIA affectation = iaService.calculerAffectation(colisId, latColis, longColis, livreurs);
        return ResponseEntity.status(HttpStatus.CREATED).body(affectation);
    }

    @GetMapping("/predictions/commande/{commandeId}")
    public ResponseEntity<List<PredictionDelai>> getPredictionsByCommande(@PathVariable String commandeId) {
        return ResponseEntity.ok(iaService.getPredictionsByCommande(commandeId));
    }

    @GetMapping("/affectations/colis/{colisId}")
    public ResponseEntity<List<AffectationIA>> getAffectationsByColis(@PathVariable String colisId) {
        return ResponseEntity.ok(iaService.getAffectationsByColis(colisId));
    }
}
