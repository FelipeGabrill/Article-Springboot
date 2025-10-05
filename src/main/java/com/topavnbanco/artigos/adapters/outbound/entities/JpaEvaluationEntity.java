package com.topavnbanco.artigos.adapters.outbound.entities;

import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_evaluation")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class JpaEvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Double finalScore;

    private Integer numberOfReviews;

    @OneToOne(optional = false)
    @JoinColumn(name = "article_id", unique = true, nullable = false)
    private JpaArticleEntity article;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaReviewEntity> reviews = new ArrayList<>();

    public JpaEvaluationEntity(Evaluation evaluation) {
        if (evaluation != null) {
            this.id = evaluation.getId();
            this.finalScore = evaluation.getFinalScore();
            this.numberOfReviews = evaluation.getNumberOfReviews();
            if (evaluation.getArticle() != null && evaluation.getArticle().getId() != null) {
                JpaArticleEntity artRef = new JpaArticleEntity();
                artRef.setId(evaluation.getArticle().getId());
                this.article = artRef;
            } else {
                this.article = null;
            }

            if (evaluation.getReviews() != null) {
                this.reviews = evaluation.getReviews().stream()
                        .filter(r -> r != null && r.getId() != null)
                        .map(r -> {
                            JpaReviewEntity ref = new JpaReviewEntity();
                            ref.setId(r.getId());
                            return ref;
                        })
                        .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
            } else {
                this.reviews = new java.util.ArrayList<>();
            }
        }
    }
}
