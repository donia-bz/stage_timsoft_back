package tn.esprit.commandes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnlevementRequest {

    @NotBlank(message = "Le clientId est obligatoire")
    private String clientId;

    @NotBlank(message = "Le manifesteId est obligatoire")
    private String manifesteId;

    private String livreurId;

    @NotNull(message = "La date demandee est obligatoire")
    private LocalDateTime dateDemandee;

    private LocalDateTime dateReelle;

    @NotBlank(message = "L'adresse enlevement est obligatoire")
    private String adresseEnlevementId;
}
