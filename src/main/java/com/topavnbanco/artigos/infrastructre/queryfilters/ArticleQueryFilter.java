package com.topavnbanco.artigos.infrastructre.queryfilters;

import com.topavnbanco.artigos.domain.article.Article;

import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.infrastructre.specifications.ArticleSpec;
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

    public Specification<Article> toSpecification() {
        return Specification.where(ArticleSpec.titleContains(title))
                .and(ArticleSpec.descriptionContains(description))
                .and(ArticleSpec.hasFormat(format))
                .and(ArticleSpec.hasStatus(status))
                .and(ArticleSpec.hasUser(user));
    }
}
