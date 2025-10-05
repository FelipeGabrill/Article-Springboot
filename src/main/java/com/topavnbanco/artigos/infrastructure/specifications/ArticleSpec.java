package com.topavnbanco.artigos.infrastructure.specifications;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaUserEntity;
import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class ArticleSpec {

    public static Specification<JpaArticleEntity> titleContains(String title) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(title)) {
                return null;
            }
            return builder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<JpaArticleEntity> knowledgeAreaContains(String knowledgeArea) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(knowledgeArea)) {
                return null;
            }
            Join<JpaArticleEntity, String> join = root.join("knowledgeArea");
            return builder.like(join, "%" + knowledgeArea + "%");
        };
    }


    public static Specification<JpaArticleEntity> descriptionContains(String description) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(description)) {
                return null;
            }
            return builder.like(root.get("description"), "%" + description + "%");
        };
    }

    public static Specification<JpaArticleEntity> hasStatus(ReviewPerArticleStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return null;
            }
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<JpaArticleEntity> hasFormat(ArticleFormat format) {
        return (root, query, builder) -> {
            if (format == null) {
                return null;
            }
            return builder.equal(root.get("format"), format);
        };
    }

    public static Specification<JpaArticleEntity> hasUser(Long userId) {
        return (root, query, builder) -> {
            if (userId == null) {
                return null;
            }
            Join<JpaArticleEntity, JpaUserEntity> join = root.join("articlesUsers");
            return builder.equal(join.get("id"), userId);
        };
    }

    public static Specification<JpaArticleEntity> hasCongressoId(Long congressoId) {
        return (root, query, builder) -> {
            if (congressoId == null) {
                return null;
            }
            Join<JpaArticleEntity, JpaUserEntity> join = root.join("congresso");
            return builder.equal(join.get("id"), congressoId);
        };
    }
}
