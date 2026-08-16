package tn.esprit.ia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandeDTO {
    private String id;
    private Double latitude;
    private Double longitude;
    private Double poidsKg;
    private String ville;
    private String gouvernorat;
}
