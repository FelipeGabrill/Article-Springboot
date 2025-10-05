package com.topavnbanco.artigos.adapters.outbound.entities;


import com.topavnbanco.artigos.domain.review.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tb_review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JpaReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String comment;

    private Integer score;

    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private JpaArticleEntity article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private JpaUserEntity reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private JpaEvaluationEntity evaluation;

    public JpaReviewEntity(Review review) {
        if (review != null) {
            this.id = review.getId();
            this.comment = review.getComment();
            this.score = review.getScore();
            this.createAt = review.getCreateAt();
            if (review.getArticle() != null && review.getArticle().getId() != null) {
                JpaArticleEntity ref = new JpaArticleEntity();
                ref.setId(review.getArticle().getId());
                this.article = ref;
            }

            if (review.getReviewer() != null && review.getReviewer().getId() != null) {
                JpaUserEntity ref = new JpaUserEntity();
                ref.setId(review.getReviewer().getId());
                this.reviewer = ref;
            }

            if (review.getEvaluation() != null && review.getEvaluation().getId() != null) {
                JpaEvaluationEntity ref = new JpaEvaluationEntity();
                ref.setId(review.getEvaluation().getId());
                this.evaluation = ref;
            }
        }
    }


}
