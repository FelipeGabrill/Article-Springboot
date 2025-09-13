package com.topavnbanco.artigos.dto;

import com.topavnbanco.artigos.dto.user.UserDTO;
import com.topavnbanco.artigos.dto.user.UserSimpleDTO;
import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewDTO {

    @Schema(description = "Identificador único da revisão", example = "101", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Comentário do revisor sobre o artigo", example = "Texto bem estruturado, porém faltam referências na seção 2.")
    @NotBlank(message = "O comentário não pode estar em branco.")
    @Size(min = 5, max = 2000, message = "O comentário deve ter entre 5 e 2000 caracteres.")
    private String comment;

    @Schema(description = "Nota atribuída ao artigo (0 a 5)", example = "9")
    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 0, message = "A nota mínima permitida é 0.")
    @Max(value = 5, message = "A nota máxima permitida é 5.")
    private Integer score;

    @Schema(description = "Identificador do artigo revisado", example = "1")
    @NotNull(message = "O artigo associado é obrigatório.")
    private Long articleId;

    @Schema(description = "Identificador da avaliação consolidada associada à revisão", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long evaluationId;

    @Schema(description = "Identificador do revisor", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private UserSimpleDTO UserDTO;

    public ReviewDTO(Review entity) {
        this.id = entity.getId();
        this.comment = entity.getComment();
        this.score = entity.getScore();
        this.articleId= entity.getArticle().getId();
        this.evaluationId = entity.getEvaluation().getId();
        this.UserDTO = new UserSimpleDTO(entity.getReviewer());
    }
}
