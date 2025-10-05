package com.topavnbanco.artigos.infrastructure.queryfilters;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;

import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.infrastructure.specifications.ArticleSpec;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ArticleQueryFilter {

    private String knowledgeArea;

    private String title;

    private String description;

    private ReviewPerArticleStatus status;

    private ArticleFormat format;

    private Long user;

    private Long congressoId;

    public Specification<JpaArticleEntity> toSpecification() {
        return Specification.where(ArticleSpec.titleContains(title))
                .and(ArticleSpec.descriptionContains(description))
                .and(ArticleSpec.hasFormat(format))
                .and(ArticleSpec.hasStatus(status))
                .and(ArticleSpec.hasUser(user))
                .and(ArticleSpec.hasCongressoId(congressoId));
    }
}
