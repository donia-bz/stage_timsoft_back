package tn.esprit.commandes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ManifesteRequest {

    @NotBlank(message = "Le clientId est obligatoire")
    private String clientId;

    @NotNull(message = "Le nombre de colis est obligatoire")
    private Integer nombreColis;

    private List<String> colisIds;
}
