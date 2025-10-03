package com.topavnbanco.artigos.domain.review;

import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tb_review")
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String comment;

    private Integer score;

    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;


    public Review() {
    }
}
