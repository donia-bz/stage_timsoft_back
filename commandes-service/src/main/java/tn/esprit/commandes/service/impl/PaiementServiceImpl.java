package tn.esprit.commandes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.commandes.dto.request.PaiementRequest;
import tn.esprit.commandes.dto.response.PaiementResponse;
import tn.esprit.commandes.entity.Commande;
import tn.esprit.commandes.entity.Paiement;
import tn.esprit.commandes.entity.enums.StatutCommande;
import tn.esprit.commandes.exception.ResourceNotFoundException;
import tn.esprit.commandes.repository.CommandeRepository;
import tn.esprit.commandes.repository.PaiementRepository;
import tn.esprit.commandes.service.PaiementService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final CommandeRepository commandeRepository;

    @Override
    public PaiementResponse enregistrerPaiement(PaiementRequest request) {
        Commande commande = commandeRepository.findById(request.getCommandeId())
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable avec l'id : " + request.getCommandeId()));

        Paiement paiement = Paiement.builder()
                .commandeId(request.getCommandeId())
                .montant(request.getMontant())
                .methode(request.getMethode())
                .statut("PAYE")
                .build();

        Paiement sauvegarde = paiementRepository.save(paiement);

        // Mettre a jour la commande
        commande.setMontantTotal(request.getMontant());
        commande.setStatut(StatutCommande.VALIDEE);
        commandeRepository.save(commande);

        return toResponse(sauvegarde);
    }

    @Override
    public PaiementResponse getPaiementById(String id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable avec l'id : " + id));
        return toResponse(paiement);
    }

    @Override
    public PaiementResponse getPaiementByCommandeId(String commandeId) {
        Paiement paiement = paiementRepository.findByCommandeId(commandeId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable pour la commande : " + commandeId));
        return toResponse(paiement);
    }

    @Override
    public PaiementResponse updateStatutPaiement(String id, String statut) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable avec l'id : " + id));
        paiement.setStatut(statut);
        Paiement updated = paiementRepository.save(paiement);
        return toResponse(updated);
    }

    @Override
    public List<PaiementResponse> getAllPaiements() {
        return paiementRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PaiementResponse toResponse(Paiement p) {
        return PaiementResponse.builder()
                .id(p.getId())
                .commandeId(p.getCommandeId())
                .montant(p.getMontant())
                .methode(p.getMethode())
                .statut(p.getStatut())
                .build();
    }
}
