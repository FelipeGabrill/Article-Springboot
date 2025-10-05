package com.topavnbanco.artigos.domain.evaluation.repository;

import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EvaluationRepository {

    Evaluation save(Evaluation evaluation);

    Optional<Evaluation> findByArticle_Id(Long articleId);

    Optional<Evaluation> findById(Long id);

    Page<Evaluation> findAll(Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);

    Evaluation getReferenceById(Long id);
}
