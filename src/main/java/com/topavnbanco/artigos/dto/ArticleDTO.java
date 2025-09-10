package com.topavnbanco.artigos.dto;

import com.topavnbanco.artigos.entities.Article;
import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.entities.enuns.ArticleFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ArticleDTO {

    @Schema(description = "Identificador único do artigo", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Descrição resumida do artigo", example = "Artigo sobre inteligência artificial")
    @NotBlank(message = "A descrição não pode estar em branco.")
    @Size(min = 5, max = 200, message = "A descrição deve ter entre 5 e 200 caracteres.")
    private String description;

    @Schema(description = "Conteúdo completo do artigo", example = "Este artigo aborda os impactos da IA na saúde...")
    @NotBlank(message = "O corpo do artigo não pode estar em branco.")
    @Size(min = 20, message = "O corpo do artigo deve ter no mínimo 20 caracteres.")
    private String body;

    @Schema(description = "Formato do artigo", example = "PDF")
    @NotNull(message = "O formato do artigo é obrigatório.")
    private ArticleFormat format;

    @Schema(description = "Artigos validos (ja revisados)", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean isValid;

    @Schema(description = "Data de publicação do artigo", example = "2025-09-08T12:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
    private Instant publishedAt;

    @Schema(description = "Id do congresso", example = "1")
    private Long congressoId;

    @Schema(description = "Lista de usuários associados ao artigo")
    private Set<Long> articlesUsersIds= new HashSet<>();

    @Schema(description = "Lista de revisões do artigo")
    private List<Long> reviewsIds = new ArrayList<>();

    public ArticleDTO(Article entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.isValid = entity.getIsValid();
        this.body = entity.getBody();
        this.format = entity.getFormat();
        this.publishedAt = entity.getPublishedAt();
        this.congressoId = entity.getCongresso().getId();

        if (entity.getArticlesUsers() != null) {
            for (User user : entity.getArticlesUsers()) {
                if (user != null && user.getId() != null) {
                    this.articlesUsersIds.add(user.getId());
                }
            }
        }
        if (entity.getReview() != null) {
            for (Review review : entity.getReview()) {
                if (review != null && review.getId() != null) {
                    this.reviewsIds.add(review.getId());
                }
            }
        }
    }
}
