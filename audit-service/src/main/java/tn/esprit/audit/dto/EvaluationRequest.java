package tn.esprit.audit.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tn.esprit.audit.entity.enums.TypeEvaluation;

@Data
public class EvaluationRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer note;

    private String commentaire;

    @NotNull
    private TypeEvaluation type;

    @NotBlank
    private String livraisonId;

    @NotBlank
    private String clientId;
}
