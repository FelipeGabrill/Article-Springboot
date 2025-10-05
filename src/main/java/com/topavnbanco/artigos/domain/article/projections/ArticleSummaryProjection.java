package com.topavnbanco.artigos.domain.article.projections;

import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;

import java.time.Instant;
import java.util.Date;

public interface ArticleSummaryProjection {


    Long getId();

    String getTitle();

    String getDescription();

    ArticleFormat getFormat();

    ReviewPerArticleStatus getStatus();

    Instant getPublishedAt();

    Long getCongressoId();

    Long getEvaluationId();

}
