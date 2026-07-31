package tn.esprit.audit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.audit.dto.HistoriqueStatutRequest;
import tn.esprit.audit.entity.HistoriqueStatut;
import tn.esprit.audit.repository.HistoriqueStatutRepository;
import tn.esprit.audit.service.HistoriqueStatutService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueStatutServiceImpl implements HistoriqueStatutService {

    private final HistoriqueStatutRepository historiqueStatutRepository;

    @Override
    public HistoriqueStatut enregistrer(HistoriqueStatutRequest request) {
        HistoriqueStatut historique = HistoriqueStatut.builder()
                .entiteType(request.getEntiteType())
                .entiteId(request.getEntiteId())
                .ancienStatut(request.getAncienStatut())
                .nouveauStatut(request.getNouveauStatut())
                .dateChangement(LocalDateTime.now())
                .auteurId(request.getAuteurId() != null ? request.getAuteurId() : "SYSTEM")
                .build();
        return historiqueStatutRepository.save(historique);
    }

    @Override
    public List<HistoriqueStatut> getByEntite(String entiteType, String entiteId) {
        return historiqueStatutRepository.findByEntiteTypeAndEntiteIdOrderByDateChangementDesc(entiteType, entiteId);
    }

    @Override
    public List<HistoriqueStatut> getByAuteur(String auteurId) {
        return historiqueStatutRepository.findByAuteurIdOrderByDateChangementDesc(auteurId);
    }
}
