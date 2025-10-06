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

}
