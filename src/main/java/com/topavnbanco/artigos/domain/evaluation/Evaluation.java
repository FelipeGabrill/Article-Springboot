package com.topavnbanco.artigos.domain.evaluation;

import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.article.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_evaluation")
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Double finalScore;

    private Integer numberOfReviews;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", unique = true, nullable = false)
    private Article article;

    @OneToMany(mappedBy = "evaluation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public Evaluation() {
    }
}
