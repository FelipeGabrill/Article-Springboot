package com.topavnbanco.artigos.infrastructre.specifications;

import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class ArticleSpec {

    public static Specification<Article> titleContains(String title) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(title)) {
                return null;
            }
            return builder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Article> knowledgeAreaContains(String knowledgeArea) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(knowledgeArea)) {
                return null;
            }
            Join<Article, String> join = root.join("knowledgeArea");
            return builder.like(join, "%" + knowledgeArea + "%");
        };
    }


    public static Specification<Article> descriptionContains(String description) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(description)) {
                return null;
            }
            return builder.like(root.get("description"), "%" + description + "%");
        };
    }

    public static Specification<Article> hasStatus(ReviewPerArticleStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return null;
            }
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<Article> hasFormat(ArticleFormat format) {
        return (root, query, builder) -> {
            if (format == null) {
                return null;
            }
            return builder.equal(root.get("format"), format);
        };
    }

    public static Specification<Article> hasUser(Long userId) {
        return (root, query, builder) -> {
            if (userId == null) {
                return null;
            }
            Join<Article, User> join = root.join("articlesUsers");
            return builder.equal(join.get("id"), userId);
        };
    }

}
