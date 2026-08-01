package tn.esprit.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.auth.dto.AdresseRequestForController;
import tn.esprit.auth.entity.Adresse;
import tn.esprit.auth.service.AdresseService;

import java.util.List;

@RestController
@RequestMapping("/api/adresses")
@RequiredArgsConstructor
public class AdresseController {

    private final AdresseService adresseService;

    @PostMapping
    public ResponseEntity<Adresse> create(@RequestBody AdresseRequestForController request) {
        Adresse adresse = Adresse.builder()
                .rue(request.getRue())
                .ville(request.getVille())
                .codePostal(request.getCodePostal())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .adressePrincipale(request.getAdressePrincipale())
                .build();
        return ResponseEntity.ok(adresseService.createAdresse(adresse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adresse> getById(@PathVariable String id) {
        return ResponseEntity.ok(adresseService.getAdresseById(id));
    }

    @GetMapping
    public ResponseEntity<List<Adresse>> getAll() {
        return ResponseEntity.ok(adresseService.getAllAdresses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Adresse> update(@PathVariable String id, @RequestBody AdresseRequestForController request) {
        Adresse adresse = Adresse.builder()
                .rue(request.getRue())
                .ville(request.getVille())
                .codePostal(request.getCodePostal())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .adressePrincipale(request.getAdressePrincipale())
                .build();
        return ResponseEntity.ok(adresseService.updateAdresse(id, adresse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        adresseService.deleteAdresse(id);
        return ResponseEntity.noContent().build();
    }
}
