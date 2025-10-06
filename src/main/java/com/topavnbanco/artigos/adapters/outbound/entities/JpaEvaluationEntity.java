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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", unique = true, nullable = false)
    private JpaArticleEntity article;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<JpaReviewEntity> reviews = new ArrayList<>();

}
