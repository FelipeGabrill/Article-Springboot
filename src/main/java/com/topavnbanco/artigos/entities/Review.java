package com.topavnbanco.artigos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "evaluation_id", unique = true)
    private Evaluation evaluation;

    public Review() {
    }
}
