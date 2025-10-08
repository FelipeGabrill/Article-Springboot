package com.topavnbanco.artigos.domain.article.repository;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.domain.article.projections.ArticleSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> findById(Long id);

    Page<Article> findAll(Specification<JpaArticleEntity> specification, Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);

    Article getReferenceById(Long id);

    Page<Article> findByCongressoId(Long congressoId, Pageable pageable);

    List<Article> findByCongressoId(Long congressoId);

    Page<Article> findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(
            Long congressoId,
            ReviewPerArticleStatus status,
            Pageable pageable
    );

    Page<ArticleSummaryProjection> findByArticlesUsers_Id(Long userId, Pageable pageable);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);
}
