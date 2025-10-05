package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleDTO;
import com.topavnbanco.artigos.infrastructure.queryfilters.ArticleQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleUseCases {

    ArticleDTO findById(Long id);

    Page<ArticleDTO> findAll(ArticleQueryFilter filter, Pageable pageable);

    Page<ArticleDTO> findTop20(Long congressoId);

    ArticleDTO insert(ArticleDTO dto);

    ArticleDTO update(Long id, ArticleDTO dto);

    void delete(Long id);
}
