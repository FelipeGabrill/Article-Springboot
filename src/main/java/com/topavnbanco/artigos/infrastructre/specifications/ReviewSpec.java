package com.topavnbanco.artigos.infrastructre.specifications;

import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.user.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpec {

    public static Specification<Review> hasUser(Long userId) {
        return (root, query, builder) -> {
            if (userId == null) {
                return null;
            }
            Join<Review, User> join = root.join("reviewer");
            return builder.equal(join.get("id"), userId);
        };
    }

    public static Specification<Review> hasArticle(Long articleId) {
        return (root, query, builder) -> {
            if (articleId == null) {
                return null;
            }
            Join<Review, Article> join = root.join("article");
            return builder.equal(join.get("id"), articleId);
        };
    }

    public static Specification<Review> hasEvaluation(Long evaluationId) {
        return (root, query, builder) -> {
            if (evaluationId == null) {
                return null;
            }
            Join<Review, Evaluation> join = root.join("evaluation");
            return builder.equal(join.get("id"), evaluationId);
        };
    }
}
