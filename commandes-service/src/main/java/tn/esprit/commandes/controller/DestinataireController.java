package tn.esprit.commandes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.commandes.dto.request.DestinataireRequest;
import tn.esprit.commandes.entity.Destinataire;
import tn.esprit.commandes.service.DestinataireService;

import java.util.List;

@RestController
@RequestMapping("/api/destinataires")
@RequiredArgsConstructor
public class DestinataireController {

    private final DestinataireService destinataireService;

    @PostMapping
    public ResponseEntity<Destinataire> create(@RequestBody DestinataireRequest request) {
        Destinataire destinataire = Destinataire.builder()
                .nom(request.getNom())
                .telephone(request.getTelephone())
                .adresseId(request.getAdresseId())
                .build();
        return ResponseEntity.ok(destinataireService.createDestinataire(destinataire));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destinataire> getById(@PathVariable String id) {
        return ResponseEntity.ok(destinataireService.getDestinataireById(id));
    }

    @GetMapping
    public ResponseEntity<List<Destinataire>> getAll() {
        return ResponseEntity.ok(destinataireService.getAllDestinataires());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destinataire> update(@PathVariable String id, @RequestBody DestinataireRequest request) {
        Destinataire destinataire = Destinataire.builder()
                .nom(request.getNom())
                .telephone(request.getTelephone())
                .adresseId(request.getAdresseId())
                .build();
        return ResponseEntity.ok(destinataireService.updateDestinataire(id, destinataire));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        destinataireService.deleteDestinataire(id);
        return ResponseEntity.noContent().build();
    }
}
