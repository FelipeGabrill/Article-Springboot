package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaEvaluationEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaEvaluationRepository extends JpaRepository<JpaEvaluationEntity, Long> {

    @EntityGraph(attributePaths = {"reviews"})
    Optional<JpaEvaluationEntity> findByArticle_Id(Long articleId);
}
