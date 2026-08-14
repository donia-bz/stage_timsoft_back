package tn.esprit.commandes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.commandes.dto.request.ColisRequest;
import tn.esprit.commandes.dto.request.CommandeRequest;
import tn.esprit.commandes.dto.response.ColisResponse;
import tn.esprit.commandes.dto.response.CommandeResponse;
import tn.esprit.commandes.entity.Adresse;
import tn.esprit.commandes.entity.Colis;
import tn.esprit.commandes.entity.Commande;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.client.AuditClient;
import tn.esprit.commandes.repository.ColisRepository;
import tn.esprit.commandes.repository.CommandeRepository;
import tn.esprit.commandes.service.CommandeService;
import tn.esprit.commandes.service.StatutTransitionService;

import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ColisRepository colisRepository;
    private final AuditClient auditClient;
    private final RestTemplate restTemplate;

    @Value("${depots.service.url:http://localhost:8087/api/depots}")
    private String depotsServiceUrl;

    @Override
    public CommandeResponse creerCommande(CommandeRequest request) {

        Commande commande = Commande.builder()
                .clientId(request.getClientId())
                .adresseDepartId(request.getAdresseDepartId()) // Optionnel
                .adresseArriveeId(request.getAdresseArriveeId()) // Optionnel
                .typeService(request.getTypeService())
                .statut(StatutCommande.EN_ATTENTE)
                .dateCreation(LocalDateTime.now())
                .delaiEstimeMin(null) // sera rempli plus tard par ia-service (PredictionDelai)
                .montantTotal(request.getMontantTotal() != null ? request.getMontantTotal() : 0.0)
                .nomDestinataire(request.getNomDestinataire())
                .telephoneDestinataire(request.getTelephoneDestinataire())
                .build();

        Commande commandeSauvegardee = commandeRepository.save(commande);

        List<Colis> colisList = request.getColis() == null ? List.of() :
                request.getColis().stream()
                        .map(cr -> toColis(cr, commandeSauvegardee.getId()))
                        .map(colisRepository::save)
                        .collect(Collectors.toList());

        return toResponse(commandeSauvegardee, colisList);
    }

    @Override
    public CommandeResponse getCommandeById(String id) {
        Commande commande = findCommandeOrThrow(id);
        List<Colis> colisList = colisRepository.findByCommandeId(id);
        return toResponse(commande, colisList);
    }

    @Override
    public List<CommandeResponse> getAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(c -> toResponse(c, colisRepository.findByCommandeId(c.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommandeResponse> getCommandesByClient(String clientId) {
        return commandeRepository.findByClientId(clientId).stream()
                .map(c -> toResponse(c, colisRepository.findByCommandeId(c.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public CommandeResponse updateStatut(String id, StatutCommande nouveauStatut) {
        Commande commande = findCommandeOrThrow(id);
        StatutCommande ancienStatut = commande.getStatut();

        // Validation de la transition selon les règles métier
        StatutTransitionService.validerTransition(ancienStatut, nouveauStatut);

        commande.setStatut(nouveauStatut);
        Commande updated = commandeRepository.save(commande);
        auditClient.enregistrerChangementStatut("Commande", id,
                ancienStatut != null ? ancienStatut.name() : null, nouveauStatut.name());
        return toResponse(updated, colisRepository.findByCommandeId(id));
    }

    @Override
    public void supprimerCommande(String id) {
        findCommandeOrThrow(id);
        colisRepository.deleteAll(colisRepository.findByCommandeId(id));
        commandeRepository.deleteById(id);
    }

    @Override
    public List<CommandeResponse> searchCommandes(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        String searchTerm = query.toLowerCase().trim();
        return commandeRepository.findAll().stream()
                .filter(cmd -> (cmd.getId() != null && cmd.getId().toLowerCase().contains(searchTerm)) ||
                            (cmd.getClientId() != null && cmd.getClientId().toLowerCase().contains(searchTerm)) ||
                            (cmd.getNomDestinataire() != null && cmd.getNomDestinataire().toLowerCase().contains(searchTerm)) ||
                            (cmd.getTelephoneDestinataire() != null && cmd.getTelephoneDestinataire().toLowerCase().contains(searchTerm)))
                .map(cmd -> toResponse(cmd, colisRepository.findByCommandeId(cmd.getId())))
                .collect(Collectors.toList());
    }

    // ---------- Helpers de mapping ----------

    private Commande findCommandeOrThrow(String id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable avec l'id : " + id));
    }

    private Colis toColis(ColisRequest cr, String commandeId) {
        String assignedDepotId = determineDepotIntelligemment();
        return Colis.builder()
                .commandeId(commandeId)
                .poids(cr.getPoids())
                .dimensions(cr.getDimensions())
                .fragile(cr.getFragile() != null && cr.getFragile())
                .statut(StatutCommande.EN_ATTENTE)
                .depotId(assignedDepotId)
                .build();
    }

    private String determineDepotIntelligemment() {
        try {
            // Appel au microservice depots-service pour récupérer les dépôts (Simulation IA d'affectation)
            ResponseEntity<List<Object>> response = restTemplate.exchange(
                    depotsServiceUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Object>>() {}
            );
            
            if (response.getBody() != null && !response.getBody().isEmpty()) {
                // Logique "IA" : Pour l'instant, on prend le premier dépôt disponible (ou on pourrait filtrer par gouvernorat)
                java.util.Map<String, Object> depot = (java.util.Map<String, Object>) response.getBody().get(0);
                return (String) depot.get("id");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'affectation IA du dépôt: " + e.getMessage());
        }
        return "DEPOT_DEFAULT";
    }

    private ColisResponse toColisResponse(Colis c) {
        return ColisResponse.builder()
                .id(c.getId())
                .commandeId(c.getCommandeId())
                .poids(c.getPoids())
                .dimensions(c.getDimensions())
                .fragile(c.getFragile())
                .statut(c.getStatut())
                .build();
    }

    private CommandeResponse toResponse(Commande c, List<Colis> colisList) {
        return CommandeResponse.builder()
                .id(c.getId())
                .clientId(c.getClientId())
                .adresseDepartId(c.getAdresseDepartId())
                .adresseArriveeId(c.getAdresseArriveeId())
                .statut(c.getStatut())
                .typeService(c.getTypeService())
                .dateCreation(c.getDateCreation())
                .delaiEstimeMin(c.getDelaiEstimeMin())
                .montantTotal(c.getMontantTotal())
                .nomDestinataire(c.getNomDestinataire())
                .telephoneDestinataire(c.getTelephoneDestinataire())
                .colis(colisList.stream().map(this::toColisResponse).collect(Collectors.toList()))
                .build();
    }
}
