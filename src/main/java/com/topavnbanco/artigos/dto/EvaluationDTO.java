package com.topavnbanco.artigos.dto;

import com.topavnbanco.artigos.entities.Evaluation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EvaluationDTO {

    @Schema(description = "Identificador único da avaliação", example = "15", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nota final da avaliação (0 a 5)", example = "3.5")
    @NotNull(message = "A nota final é obrigatória.")
    @DecimalMin(value = "0.0", message = "A nota mínima permitida é 0.0.")
    @DecimalMax(value = "5.0", message = "A nota máxima permitida é 5.0.")
    private Double finalScore;

    @Schema(description = "Número de revisões consideradas na avaliação", example = "3")
    @NotNull(message = "O número de revisões é obrigatório.")
    @Min(value = 1, message = "Deve haver pelo menos 1 revisão.")
    @Max(value = 5, message = "O número máximo de revisões permitidas é 5.")
    private Integer numberOfReviews;

    @Schema(description = "Identificador da revisão associada", example = "1")
    @NotNull(message = "A revisão associada é obrigatória.")
    private Long reviewId;

    public EvaluationDTO(Evaluation entity) {
        this.id = entity.getId();
        this.finalScore = entity.getFinalScore();
        this.numberOfReviews = entity.getNumberOfReviews();
        this.reviewId = entity.getReview().getId();
    }
}
