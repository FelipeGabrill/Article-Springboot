package com.topavnbanco.artigos.adapters.inbound.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleUserDTO;
import com.topavnbanco.artigos.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class UserArticleDTO extends UserSimpleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Lista de artigos associados ao usuario", accessMode = Schema.AccessMode.READ_ONLY)
    private List<ArticleUserDTO> articleUserDTO;

    public UserArticleDTO(User entity, List<ArticleUserDTO> articleUserDTO) {
        super(entity);
        this.articleUserDTO = articleUserDTO;
    }
}
