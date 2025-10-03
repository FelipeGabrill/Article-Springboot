package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    List<Article> findByCongressoId(Long congressoId);

    Page<Article> findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(
            Long congressoId,
            ReviewPerArticleStatus status,
            Pageable pageable
    );

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);

}