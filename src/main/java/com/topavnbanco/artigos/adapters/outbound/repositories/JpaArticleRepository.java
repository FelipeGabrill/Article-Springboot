package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.domain.article.projections.ArticleSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JpaArticleRepository extends JpaRepository<JpaArticleEntity, Long>, JpaSpecificationExecutor<JpaArticleEntity> {

    List<JpaArticleEntity> findByCongressoId(Long congressoId);

    Page<JpaArticleEntity> findByCongressoId(Long congressoId, Pageable pageable);

    Page<JpaArticleEntity> findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(
            Long congressoId,
            ReviewPerArticleStatus status,
            Pageable pageable
    );

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);

    Page<ArticleSummaryProjection> findByArticlesUsers_Id(Long userId, Pageable pageable);

}