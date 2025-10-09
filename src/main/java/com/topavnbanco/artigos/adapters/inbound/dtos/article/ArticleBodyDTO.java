package com.topavnbanco.artigos.adapters.inbound.dtos.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleBodyDTO {

    @Schema(description = "Conteúdo completo do artigo", example = "Este artigo aborda os impactos da IA na saúde...", accessMode = Schema.AccessMode.READ_ONLY)
    private String body;

    public ArticleBodyDTO(String body) {
        this.body = body;
    }
}
