package com.topavnbanco.artigos.infrastructure.specifications;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaEvaluationEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaReviewEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaUserEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpec {

    public static Specification<JpaReviewEntity> hasUser(Long userId) {
        return (root, query, builder) -> {
            if (userId == null) {
                return null;
            }
            Join<JpaReviewEntity, JpaUserEntity> join = root.join("reviewer");
            return builder.equal(join.get("id"), userId);
        };
    }

    public static Specification<JpaReviewEntity> hasArticle(Long articleId) {
        return (root, query, builder) -> {
            if (articleId == null) {
                return null;
            }
            Join<JpaReviewEntity, JpaArticleEntity> join = root.join("article");
            return builder.equal(join.get("id"), articleId);
        };
    }

    public static Specification<JpaReviewEntity> hasEvaluation(Long evaluationId) {
        return (root, query, builder) -> {
            if (evaluationId == null) {
                return null;
            }
            Join<JpaReviewEntity, JpaEvaluationEntity> join = root.join("evaluation");
            return builder.equal(join.get("id"), evaluationId);
        };
    }
}
