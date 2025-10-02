package com.topavnbanco.artigos.queryfilters;

import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.specifications.ReviewSpec;
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
