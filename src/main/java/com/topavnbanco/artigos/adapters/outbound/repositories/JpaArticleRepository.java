package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaArticleRepository extends JpaRepository<JpaArticleEntity, Long>, JpaSpecificationExecutor<JpaArticleEntity> {

    List<JpaArticleEntity> findByCongressoId(Long congressoId);

    Page<JpaArticleEntity> findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(
            Long congressoId,
            ReviewPerArticleStatus status,
            Pageable pageable
    );

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);

}