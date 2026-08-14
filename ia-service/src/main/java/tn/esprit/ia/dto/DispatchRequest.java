package tn.esprit.ia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispatchRequest {
    private List<CommandeDTO> commandes;
    private List<LivreurDTO> livreurs;
}
