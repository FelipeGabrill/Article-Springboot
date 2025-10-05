package com.topavnbanco.artigos.adapters.inbound.dtos.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.*;

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

    @Schema(description = "Título do artigo", example = "Impactos da IA na saúde")
    @NotBlank(message = "O título do artigo não pode estar em branco.")
    @Size(max = 50, message = "O título do artigo deve ter no máximo 50 caracteres.")
    private String title;

    @Schema(description = "Formato do artigo", example = "PDF", accessMode = Schema.AccessMode.READ_ONLY)
    private ArticleFormat format;

    @Schema(description = "Status do artigo", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private ReviewPerArticleStatus status;

    @Schema(description = "Data de publicação do artigo", example = "2025-09-08T12:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
    private Instant publishedAt;

    @Schema(description = "Id do congresso", example = "2")
    @NotNull(message = "O artigo deve estar contido em um congresso")
    private Long congressoId;

    @Schema(description = "Lista de usuários associados ao artigo")
    @NotNull(message = "ID de pelo menos um usuário é obrigatório")
    private Set<Long> articlesUsersIds= new HashSet<>();

    @Schema(description = "Lista de revisões do artigo", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Long> reviewsIds = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador da avaliação do artigo (se existir)", example = "15", accessMode = Schema.AccessMode.READ_ONLY)
    private Long evaluationId;

    @Schema(description = "Áreas de conhecimento relacionadas ao congresso", example = "[\"Inteligência Artificial\", \"Ciência de Dados\", \"Engenharia de Software\"]")
    private Set<String> knowledgeArea = new LinkedHashSet<>();

    public ArticleDTO(Article entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.status = entity.getStatus();
        this.body = entity.getBody();
        this.format = entity.getFormat();
        this.publishedAt = entity.getPublishedAt();
        this.title = entity.getTitle();
        this.congressoId = (entity.getCongresso() != null ? entity.getCongresso().getId() : null);
        this.evaluationId = (entity.getEvaluation() != null ? entity.getEvaluation().getId() : null);

        if (entity.getArticlesUsers() != null) {
            for (User user : entity.getArticlesUsers()) {
                if (user != null && user.getId() != null) {
                    this.articlesUsersIds.add(user.getId());
                }
            }
        }
        if (entity.getReviews() != null) {
            for (Review review : entity.getReviews()) {
                if (review != null && review.getId() != null) {
                    this.reviewsIds.add(review.getId());
                }
            }
        }

        if (entity.getKnowledgeArea() != null) {
            for (String area : entity.getKnowledgeArea()) {
                if (area != null && !area.isBlank()) {
                    this.knowledgeArea.add(area);
                }
            }
        }
    }
}
