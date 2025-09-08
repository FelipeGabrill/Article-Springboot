package com.topavnbanco.artigos.dto.user;

import com.topavnbanco.artigos.dto.ArticleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserArticlesDTO {

    private Long userId;
    private String login;
    private List<ArticleDTO> articles;
}
