package tn.esprit.ia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispatchResponse {
    // Map<LivreurId, List<CommandeId>>
    private Map<String, List<String>> affectations;
    private int totalCommandesTraitees;
}
