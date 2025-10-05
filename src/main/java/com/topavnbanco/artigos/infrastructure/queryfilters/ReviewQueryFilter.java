package com.topavnbanco.artigos.infrastructure.queryfilters;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaReviewEntity;
import com.topavnbanco.artigos.infrastructure.specifications.ReviewSpec;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ReviewQueryFilter {

    private Long articleId;

    private Long userId;

    private Long evaluationId;

    public Specification<JpaReviewEntity> toSpecification() {
        return Specification.where(ReviewSpec.hasUser(userId))
                .and(ReviewSpec.hasArticle(articleId))
                .and(ReviewSpec.hasEvaluation(evaluationId));
    }
}
