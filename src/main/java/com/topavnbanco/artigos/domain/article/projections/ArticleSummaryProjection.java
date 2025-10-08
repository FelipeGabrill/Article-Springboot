package com.topavnbanco.artigos.domain.article.projections;

import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;

public interface ArticleSummaryProjection {


    Long getId();

    String getTitle();

    String getDescription();

    ArticleFormat getFormat();

}
