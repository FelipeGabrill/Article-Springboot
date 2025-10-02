package com.topavnbanco.artigos.specifications;

import com.topavnbanco.artigos.entities.Article;
import com.topavnbanco.artigos.entities.Evaluation;
import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.entities.User;
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
