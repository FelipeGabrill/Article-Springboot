package com.topavnbanco.artigos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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

    @OneToOne(mappedBy = "evaluation", fetch = FetchType.LAZY)
    private Review review;

    public Evaluation() {
    }
}
