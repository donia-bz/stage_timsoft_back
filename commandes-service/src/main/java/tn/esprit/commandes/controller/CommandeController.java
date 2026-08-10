package tn.esprit.commandes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.commandes.dto.request.CommandeRequest;
import tn.esprit.commandes.dto.response.CommandeResponse;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.service.CommandeService;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public ResponseEntity<CommandeResponse> creer(@Valid @RequestBody CommandeRequest request) {
        CommandeResponse response = commandeService.creerCommande(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(commandeService.getCommandeById(id));
    }

    @GetMapping
    public ResponseEntity<List<CommandeResponse>> getAll() {
        return ResponseEntity.ok(commandeService.getAllCommandes());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CommandeResponse>> getByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(commandeService.getCommandesByClient(clientId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommandeResponse>> search(@RequestParam String q) {
        return ResponseEntity.ok(commandeService.searchCommandes(q));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<CommandeResponse> updateStatut(
            @PathVariable String id,
            @RequestParam StatutCommande statut) {
        return ResponseEntity.ok(commandeService.updateStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable String id) {
        commandeService.supprimerCommande(id);
        return ResponseEntity.noContent().build();
    }
}
