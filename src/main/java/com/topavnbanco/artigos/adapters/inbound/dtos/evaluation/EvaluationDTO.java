package com.topavnbanco.artigos.adapters.inbound.dtos.evaluation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.domain.review.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
public class EvaluationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador único da avaliação", example = "15", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador do artigo avaliado", example = "42")
    private Long articleId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Nota final da avaliação (0 a 5)", example = "3.5", accessMode = Schema.AccessMode.READ_ONLY)
    private Double finalScore;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Número de revisões consideradas na avaliação", example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer numberOfReviews;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "IDs das reviews consideradas na avaliação", example = "[10, 11, 12]")
    private List<Long> reviewIds;

    public EvaluationDTO(Evaluation entity) {
        this.id = entity.getId();
        this.articleId = entity.getArticle() != null ? entity.getArticle().getId() : null;
        this.finalScore = entity.getFinalScore();
        this.numberOfReviews = entity.getNumberOfReviews();
        this.reviewIds = (entity.getReviews() == null) ? List.of()
                : entity.getReviews().stream()
                .map(Review::getId)
                .filter(Objects::nonNull)
                .toList();
    }
}
