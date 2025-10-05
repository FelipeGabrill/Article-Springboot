package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaEvaluationEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaEvaluationRepository;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.domain.evaluation.repository.EvaluationRepository;
import com.topavnbanco.artigos.domain.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EvaluationRepositoryImpl implements EvaluationRepository {

    @Autowired
    private JpaEvaluationRepository jpaEvaluationRepository;

    @Override
    public Evaluation save(Evaluation evaluation) {
        JpaEvaluationEntity e = jpaEvaluationRepository.save(new JpaEvaluationEntity(evaluation));
        return new Evaluation(
                e.getId(),
                e.getFinalScore(),
                e.getNumberOfReviews(),
                new Article(e.getArticle().getId(), null, null, null, null, null, null, null, null, null, null, null),
                Collections.emptyList());
    }

    @Override
    public Optional<Evaluation> findByArticle_Id(Long articleId) {
        return jpaEvaluationRepository.findByArticle_Id(articleId)
                .map(e -> new Evaluation(
                        e.getId(),
                        e.getFinalScore(),
                        e.getNumberOfReviews(),
                        e.getArticle() != null
                                ? new Article(e.getArticle().getId(), null, null, null, null, null, null,
                                null, null, null, null, null)
                                : null,
                        e.getReviews() != null
                                ? e.getReviews().stream()
                                .map(r -> new Review(
                                        r.getId(),
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                ))
                                .toList()
                                : Collections.emptyList()
                ));
    }

    @Override
    public Optional<Evaluation> findById(Long id) {
        return jpaEvaluationRepository.findById(id)
                .map(e -> new Evaluation(
                        e.getId(),
                        e.getFinalScore(),
                        e.getNumberOfReviews(),
                        new Article(e.getArticle().getId(), null, null, null, null, null, null, null, null, null, null, null),
                        e.getReviews() != null
                                ? e.getReviews().stream()
                                .map(r -> new Review(
                                        r.getId(),
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                ))
                                .collect(Collectors.toList())
                                : new ArrayList<>()
                ));
    }

    @Override
    public Page<Evaluation> findAll(Pageable pageable) {
        return jpaEvaluationRepository.findAll(pageable).map(e -> new Evaluation(
                e.getId(),
                e.getFinalScore(),
                e.getNumberOfReviews(),
                new Article(e.getArticle().getId(), null, null, null, null, null, null, null, null, null, null, null),

                e.getReviews() != null
                        ? e.getReviews().stream()
                        .map(r -> new Review(
                                r.getId(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                        ))
                        .collect(Collectors.toList())
                        : new ArrayList<>()
        ));
    }

    @Override
    public void deleteById(Long id) {
        jpaEvaluationRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaEvaluationRepository.existsById(id);
    }

    @Override
    public Evaluation getReferenceById(Long id) {
        JpaEvaluationEntity e = jpaEvaluationRepository.getReferenceById(id);
        return new Evaluation(
                e.getId(),
                e.getFinalScore(),
                e.getNumberOfReviews(),
                new Article(e.getArticle().getId(), null, null, null, null, null, null, null, null, null, null, null),
                e.getReviews() != null
                        ? e.getReviews().stream()
                        .map(r -> new Review(
                                r.getId(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                        ))
                        .collect(Collectors.toList())
                        : new ArrayList<>()
        );
    }
}
