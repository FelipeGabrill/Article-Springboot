package com.topavnbanco.artigos.adapters.inbound.dtos.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleUserDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador único do artigo", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Descrição resumida do artigo", example = "Artigo sobre inteligência artificial", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "A descrição não pode estar em branco.")
    @Size(min = 5, max = 200, message = "A descrição deve ter entre 5 e 200 caracteres.")
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Título do artigo", example = "Impactos da IA na saúde", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank(message = "O título do artigo não pode estar em branco.")
    @Size(max = 50, message = "O título do artigo deve ter no máximo 50 caracteres.")
    private String title;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Formato do artigo", example = "PDF", accessMode = Schema.AccessMode.READ_ONLY)
    private ArticleFormat format;

    public ArticleUserDTO(Long id, String title, String description, ArticleFormat format) {
        this.description = description;
        this.title = title;
        this.format = format;
        this.id = id;
    }
}
