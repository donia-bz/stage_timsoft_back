package tn.esprit.audit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HistoriqueStatutRequest {

    @NotBlank
    private String entiteType;

    @NotBlank
    private String entiteId;

    private String ancienStatut;

    @NotBlank
    private String nouveauStatut;

    private String auteurId;
}
