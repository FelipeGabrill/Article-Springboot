package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleDTO;
import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleSimpleDTO;
import com.topavnbanco.artigos.infrastructure.queryfilters.ArticleQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleUseCases {

    ArticleSimpleDTO findById(Long id);

    Page<ArticleSimpleDTO> findAll(ArticleQueryFilter filter, Pageable pageable);

    Page<ArticleSimpleDTO> findTop20(Long congressoId);

    ArticleSimpleDTO insert(ArticleDTO dto);

    ArticleSimpleDTO update(Long id, ArticleDTO dto);

    void delete(Long id);

    String findArticleBodyById(Long id);
}
