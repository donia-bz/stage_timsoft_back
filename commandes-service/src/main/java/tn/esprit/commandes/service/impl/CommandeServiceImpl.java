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
import tn.esprit.commandes.entity.enums.StatutColis;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.repository.ColisRepository;
import tn.esprit.commandes.repository.CommandeRepository;
import tn.esprit.commandes.service.CommandeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ColisRepository colisRepository;

    @Override
    public CommandeResponse creerCommande(CommandeRequest request) {

        Commande commande = Commande.builder()
                .clientId(request.getClientId())
                .adresseDepart(toAdresse(request.getAdresseDepart()))
                .adresseArrivee(toAdresse(request.getAdresseArrivee()))
                .typeService(request.getTypeService())
                .statut(StatutCommande.EN_ATTENTE)
                .dateCreation(LocalDateTime.now())
                .delaiEstimeMin(null) // sera rempli plus tard par ia-service (PredictionDelai)
                .montantTotal(0.0)
                .build();

        Commande commandeSauvegardee = commandeRepository.save(commande);

        List<Colis> colisList = request.getColis() == null ? List.of() :
                request.getColis().stream()
                        .map(cr -> toColis(cr, commandeSauvegardee.getId()))
                        .map(colisRepository::save)
                        .collect(Collectors.toList());

        commandeSauvegardee.setColisIds(colisList.stream().map(Colis::getId).collect(Collectors.toList()));
        commandeRepository.save(commandeSauvegardee);

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
    public CommandeResponse updateStatut(String id, String nouveauStatut) {
        Commande commande = findCommandeOrThrow(id);
        commande.setStatut(StatutCommande.valueOf(nouveauStatut));
        Commande updated = commandeRepository.save(commande);
        return toResponse(updated, colisRepository.findByCommandeId(id));
    }

    @Override
    public void supprimerCommande(String id) {
        findCommandeOrThrow(id);
        colisRepository.deleteAll(colisRepository.findByCommandeId(id));
        commandeRepository.deleteById(id);
    }

    // ---------- Helpers de mapping ----------

    private Commande findCommandeOrThrow(String id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable avec l'id : " + id));
    }

    private Adresse toAdresse(tn.esprit.commandes.dto.request.AdresseRequest ar) {
        return Adresse.builder()
                .rue(ar.getRue())
                .ville(ar.getVille())
                .codePostal(ar.getCodePostal())
                .latitude(ar.getLatitude())
                .longitude(ar.getLongitude())
                .build();
    }

    private Colis toColis(ColisRequest cr, String commandeId) {
        return Colis.builder()
                .commandeId(commandeId)
                .poids(cr.getPoids())
                .dimensions(cr.getDimensions())
                .fragile(cr.getFragile() != null && cr.getFragile())
                .statut(StatutColis.EN_ATTENTE)
                .build();
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
                .adresseDepart(c.getAdresseDepart())
                .adresseArrivee(c.getAdresseArrivee())
                .statut(c.getStatut())
                .typeService(c.getTypeService())
                .dateCreation(c.getDateCreation())
                .delaiEstimeMin(c.getDelaiEstimeMin())
                .montantTotal(c.getMontantTotal())
                .colis(colisList.stream().map(this::toColisResponse).collect(Collectors.toList()))
                .build();
    }
}
