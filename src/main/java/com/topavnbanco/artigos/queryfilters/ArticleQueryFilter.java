package com.topavnbanco.artigos.queryfilters;

import com.topavnbanco.artigos.entities.Article;

import com.topavnbanco.artigos.entities.enuns.ArticleFormat;
import com.topavnbanco.artigos.entities.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.specifications.ArticleSpec;
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
