package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaEvaluationEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaReviewEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaUserEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaArticleRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaReviewRepository;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.domain.article.repository.ArticleRepository;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    @Autowired
    private JpaArticleRepository jpaArticleRepository;

    @Autowired
    private JpaReviewRepository jpaReviewRepository;


    @Override
    public Article save(Article article) {
        JpaArticleEntity entity;

        if (article.getId() == null) {
            entity = new JpaArticleEntity(article);
            entity = jpaArticleRepository.save(entity);
        } else {
            entity = jpaArticleRepository.findById(article.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Article not found: " + article.getId()));

            entity.setTitle(article.getTitle());
            entity.setDescription(article.getDescription());
            entity.setBody(article.getBody());
            entity.setKnowledgeArea(article.getKnowledgeArea());
            entity.setFormat(article.getFormat());
            entity.setStatus(article.getStatus());
            entity.setPublishedAt(article.getPublishedAt());

            if (article.getEvaluation() != null) {
                JpaEvaluationEntity eval = entity.getEvaluation();
                if (eval == null) {
                    eval = new JpaEvaluationEntity();
                    eval.setArticle(entity);
                    entity.setEvaluation(eval);
                }
                eval.setFinalScore(article.getEvaluation().getFinalScore());
                eval.setNumberOfReviews(article.getEvaluation().getNumberOfReviews());
            }


            if (article.getReviews() != null) {
                entity.getReviews().clear();
                for (Review r : article.getReviews()) {
                    JpaReviewEntity reviewEntity;
                    if (r.getId() != null) {
                        reviewEntity = jpaReviewRepository.findById(r.getId())
                                .orElse(new JpaReviewEntity(r));
                    } else {
                        reviewEntity = new JpaReviewEntity(r);
                    }
                    reviewEntity.setArticle(entity); // referência de volta
                    entity.getReviews().add(reviewEntity);
                }
            }

            if (article.getArticlesUsers() != null) {
                entity.getArticlesUsers().clear();
                for (var u : article.getArticlesUsers()) {
                    JpaUserEntity userEntity = new JpaUserEntity();
                    userEntity.setId(u.getId()); // apenas referência gerenciada
                    entity.getArticlesUsers().add(userEntity);
                }
            }

            entity = jpaArticleRepository.save(entity);
        }

        return toDomain(entity);
    }

    @Override
    public Optional<Article> findById(Long id) {
        Optional<JpaArticleEntity> optionalEntity = jpaArticleRepository.findById(id);

        if (optionalEntity.isPresent()) {
            JpaArticleEntity entity = optionalEntity.get();
            Article article = toDomain(entity);

            article.setBody(entity.getBody());

            return Optional.of(article);
        }

        return Optional.empty();
    }

    @Override
    public Page<Article> findAll(Specification<JpaArticleEntity> specification, Pageable pageable) {
        return jpaArticleRepository.findAll(specification, pageable).map(this::toDomain);
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
        JpaArticleEntity a = jpaArticleRepository.getReferenceById(id);
        return toDomain(a);
    }

    @Override
    public List<Article> findByCongressoId(Long congressoId) {
        return jpaArticleRepository.findByCongressoId(congressoId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Article> findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(Long congressoId, ReviewPerArticleStatus status, Pageable pageable) {
        return jpaArticleRepository.findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(congressoId, status, pageable).map(this::toDomain);
    }

    @Override
    public boolean existsByTitle(String title) {
        return jpaArticleRepository.existsByTitle(title);
    }

    @Override
    public boolean existsByTitleAndIdNot(String title, Long id) {
        return jpaArticleRepository.existsByTitleAndIdNot(title, id);
    }

    private Article toDomain(JpaArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        Article article = new Article();
        article.setId(entity.getId());
        article.setKnowledgeArea(entity.getKnowledgeArea());
        article.setTitle(entity.getTitle());
        article.setDescription(entity.getDescription());
        article.setStatus(entity.getStatus());
        article.setPublishedAt(entity.getPublishedAt());
        article.setFormat(entity.getFormat());

        if (entity.getCongresso() != null) {
            Congresso congresso = new Congresso();
            congresso.setId(entity.getCongresso().getId());
            congresso.setName(entity.getCongresso().getName());
            article.setCongresso(congresso);
        }

        if (entity.getArticlesUsers() != null && !entity.getArticlesUsers().isEmpty()) {
            Set<User> users = entity.getArticlesUsers().stream().map(u -> {
                User user = new User();
                user.setId(u.getId());
                user.setUsernameUser(u.getUsernameUser());
                user.setLogin(u.getLogin());
                user.setWorkPlace(u.getWorkPlace());
                user.setReviewer(u.getIsReviewer());
                return user;
            }).collect(Collectors.toSet());
            article.setArticlesUsers(users);
        }

        if (entity.getEvaluation() != null) {
            Evaluation eval = new Evaluation();
            eval.setId(entity.getEvaluation().getId());
            eval.setFinalScore(entity.getEvaluation().getFinalScore());
            eval.setNumberOfReviews(entity.getEvaluation().getNumberOfReviews());
            article.setEvaluation(eval);
        }

        if (entity.getReviews() != null && !entity.getReviews().isEmpty()) {
            List<Review> reviews = entity.getReviews().stream().map(r -> {
                Review review = new Review();
                review.setId(r.getId());
                review.setScore(r.getScore());
                if (r.getReviewer() != null) {
                    User reviewer = new User();
                    reviewer.setId(r.getReviewer().getId());
                    reviewer.setUsernameUser(r.getReviewer().getUsernameUser());
                    review.setReviewer(reviewer);
                }
                return review;
            }).collect(Collectors.toList());
            article.setReviews(reviews);
        }

        return article;
    }



}
