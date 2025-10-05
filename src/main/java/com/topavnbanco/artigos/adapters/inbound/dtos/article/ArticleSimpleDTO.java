package com.topavnbanco.artigos.adapters.inbound.dtos.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.*;

public class ArticleSimpleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador único do artigo", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Descrição resumida do artigo", example = "Artigo sobre inteligência artificial")
    @NotBlank(message = "A descrição não pode estar em branco.")
    @Size(min = 5, max = 200, message = "A descrição deve ter entre 5 e 200 caracteres.")
    private String description;

    @Schema(description = "Título do artigo", example = "Impactos da IA na saúde")
    @NotBlank(message = "O título do artigo não pode estar em branco.")
    @Size(max = 50, message = "O título do artigo deve ter no máximo 50 caracteres.")
    private String title;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Formato do artigo", example = "PDF", accessMode = Schema.AccessMode.READ_ONLY)
    private ArticleFormat format;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Status do artigo", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private ReviewPerArticleStatus status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Data de publicação do artigo", example = "2025-09-08T12:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
    private Instant publishedAt;

    @Schema(description = "Id do congresso", example = "2")
    @NotNull(message = "O artigo deve estar contido em um congresso")
    private Long congressoId;

    @Schema(description = "Lista de usuários associados ao artigo")
    @NotNull(message = "ID de pelo menos um usuário é obrigatório")
    private Set<Long> articlesUsersIds= new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador da avaliação do artigo (se existir)", example = "15", accessMode = Schema.AccessMode.READ_ONLY)
    private Long evaluationId;

    @Schema(description = "Áreas de conhecimento relacionadas ao congresso", example = "[\"Inteligência Artificial\", \"Ciência de Dados\", \"Engenharia de Software\"]")
    private Set<String> knowledgeArea = new LinkedHashSet<>();

    public ArticleSimpleDTO(Article entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.status = entity.getStatus();
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

        if (entity.getKnowledgeArea() != null) {
            for (String area : entity.getKnowledgeArea()) {
                if (area != null && !area.isBlank()) {
                    this.knowledgeArea.add(area);
                }
            }
        }
    }
}
