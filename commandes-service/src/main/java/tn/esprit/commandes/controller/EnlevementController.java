package tn.esprit.commandes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.commandes.dto.request.EnlevementRequest;
import tn.esprit.commandes.entity.Enlevement;
import tn.esprit.commandes.entity.enums.StatutEnlevement;
import tn.esprit.commandes.service.EnlevementService;

import java.util.List;

@RestController
@RequestMapping("/api/enlevements")
@RequiredArgsConstructor
public class EnlevementController {

    private final EnlevementService enlevementService;

    @PostMapping
    public ResponseEntity<Enlevement> create(@RequestBody EnlevementRequest request) {
        Enlevement enlevement = Enlevement.builder()
                .clientId(request.getClientId())
                .manifesteId(request.getManifesteId())
                .livreurId(request.getLivreurId())
                .dateDemandee(request.getDateDemandee())
                .dateReelle(request.getDateReelle())
                .statut(StatutEnlevement.EN_ATTENTE)
                .adresseEnlevementId(request.getAdresseEnlevementId())
                .build();
        return ResponseEntity.ok(enlevementService.createEnlevement(enlevement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enlevement> getById(@PathVariable String id) {
        return ResponseEntity.ok(enlevementService.getEnlevementById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Enlevement>> getByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(enlevementService.getEnlevementsByClient(clientId));
    }

    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<Enlevement>> getByLivreur(@PathVariable String livreurId) {
        return ResponseEntity.ok(enlevementService.getEnlevementsByLivreur(livreurId));
    }

    @GetMapping
    public ResponseEntity<List<Enlevement>> getAll() {
        return ResponseEntity.ok(enlevementService.getAllEnlevements());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enlevement> update(@PathVariable String id, @RequestBody EnlevementRequest request) {
        Enlevement existing = enlevementService.getEnlevementById(id);
        if (request.getClientId() != null) {
            existing.setClientId(request.getClientId());
        }
        if (request.getManifesteId() != null) {
            existing.setManifesteId(request.getManifesteId());
        }
        if (request.getLivreurId() != null) {
            existing.setLivreurId(request.getLivreurId());
        }
        if (request.getDateDemandee() != null) {
            existing.setDateDemandee(request.getDateDemandee());
        }
        if (request.getDateReelle() != null) {
            existing.setDateReelle(request.getDateReelle());
        }
        if (request.getAdresseEnlevementId() != null) {
            existing.setAdresseEnlevementId(request.getAdresseEnlevementId());
        }
        return ResponseEntity.ok(enlevementService.updateEnlevement(id, existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        enlevementService.deleteEnlevement(id);
        return ResponseEntity.noContent().build();
    }
}
