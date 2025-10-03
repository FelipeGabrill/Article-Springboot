package com.topavnbanco.artigos.infrastructre.queryfilters;

import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.infrastructre.specifications.ReviewSpec;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ReviewQueryFilter {

    private Long articleId;

    private Long userId;

    private Long evaluationId;

    public Specification<Review> toSpecification() {
        return Specification.where(ReviewSpec.hasUser(userId))
                .and(ReviewSpec.hasArticle(articleId))
                .and(ReviewSpec.hasEvaluation(evaluationId));
    }
}
