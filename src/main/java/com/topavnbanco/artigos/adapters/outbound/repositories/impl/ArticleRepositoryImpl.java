package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaArticleRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaReviewRepository;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.domain.article.projections.ArticleSummaryProjection;
import com.topavnbanco.artigos.domain.article.repository.ArticleRepository;
import com.topavnbanco.artigos.infrastructure.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    @Autowired
    private JpaArticleRepository jpaArticleRepository;

    @Autowired
    private JpaReviewRepository jpaReviewRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article save(Article article) {
        JpaArticleEntity entity = articleMapper.toEntity(article);
        entity = jpaArticleRepository.save(entity);
        return articleMapper.toDomain(entity);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return jpaArticleRepository.findById(id).map(articleMapper::toDomain);
    }

    @Override
    public Page<Article> findAll(Specification<JpaArticleEntity> specification, Pageable pageable) {
        return jpaArticleRepository.findAll(specification, pageable).map(articleMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaArticleRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaArticleRepository.existsById(id);
    }

    @Override
    public Article getReferenceById(Long id) {
        return articleMapper.toDomain(jpaArticleRepository.getReferenceById(id));
    }

    @Override
    public List<Article> findByCongressoId(Long congressoId) {
        return articleMapper.toDomainList(jpaArticleRepository.findByCongressoId(congressoId));
    }

    @Override
    public Page<Article> findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(Long congressoId, ReviewPerArticleStatus status, Pageable pageable) {
        return jpaArticleRepository.findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(congressoId, status, pageable)
                .map(articleMapper::toDomain);
    }

    @Override
    public Page<ArticleSummaryProjection> findByArticlesUsers_Id(Long userId, Pageable pageable) {
        return jpaArticleRepository.findByArticlesUsers_Id(userId, pageable);
    }

    @Override
    public boolean existsByTitle(String title) {
        return jpaArticleRepository.existsByTitle(title);
    }

    @Override
    public boolean existsByTitleAndIdNot(String title, Long id) {
        return jpaArticleRepository.existsByTitleAndIdNot(title, id);
    }
}
