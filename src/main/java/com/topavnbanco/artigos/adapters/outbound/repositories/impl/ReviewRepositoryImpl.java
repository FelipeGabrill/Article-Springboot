package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaEvaluationEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaReviewEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaUserEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaReviewRepository;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.review.repository.ReviewRepository;
import com.topavnbanco.artigos.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    @Autowired
    private JpaReviewRepository jpaReviewRepository;

    @Override
    public Review save(Review review) {
        if (review.getId() == null) {
            JpaReviewEntity toSave = new JpaReviewEntity(review);
            JpaReviewEntity saved = jpaReviewRepository.save(toSave);
            return toDomain(saved);
        } else {
            Optional<JpaReviewEntity> optionalEntity = jpaReviewRepository.findById(review.getId());
            JpaReviewEntity entity = optionalEntity.get();

            entity.setComment(review.getComment());
            entity.setCreateAt(review.getCreateAt());
            entity.setScore(review.getScore());

            if (review.getReviewer() != null) {
                if (entity.getReviewer() == null) {
                    entity.setReviewer(new JpaUserEntity());
                }
                entity.getReviewer().setId(review.getReviewer().getId());
                entity.getReviewer().setUsernameUser(review.getReviewer().getUsernameUser());
                entity.getReviewer().setLogin(review.getReviewer().getLogin());
                entity.getReviewer().setWorkPlace(review.getReviewer().getWorkPlace());
                entity.getReviewer().setIsReviewer(review.getReviewer().getReviewer());
            }

            if (review.getArticle() != null) {
                if (entity.getArticle() == null) {
                    entity.setArticle(new JpaArticleEntity());
                }
                entity.getArticle().setId(review.getArticle().getId());
                entity.getArticle().setTitle(review.getArticle().getTitle());
            }

            if (review.getEvaluation() != null) {
                if (entity.getEvaluation() == null) {
                    entity.setEvaluation(new JpaEvaluationEntity(review.getEvaluation()));
                } else {
                    entity.getEvaluation().setFinalScore(review.getEvaluation().getFinalScore());
                    entity.getEvaluation().setNumberOfReviews(review.getEvaluation().getNumberOfReviews());
                }
            }

            JpaReviewEntity saved = jpaReviewRepository.save(entity);
            return toDomain(saved);
        }
    }


    @Override
    public void saveAll(List<Review> review) {
        List<JpaReviewEntity> list = review.stream().map(JpaReviewEntity::new).toList();
        jpaReviewRepository.saveAll(list);
    }

    @Override
    public Optional<Review> findById(Long id) {
        Optional<JpaReviewEntity> optionalEntity = jpaReviewRepository.findById(id);

        if (optionalEntity.isPresent()) {
            JpaReviewEntity entity = optionalEntity.get();
            Review review = toDomain(entity);

            return Optional.of(review);
        }

        return Optional.empty();
    }

    @Override
    public Page<Review> findAll(Specification<JpaReviewEntity> specification, Pageable pageable) {
        return jpaReviewRepository.findAll(specification, pageable).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaReviewRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Review> reviews) {
        List<JpaReviewEntity> entities = reviews.stream()
                .map(JpaReviewEntity::new)
                .toList();
        jpaReviewRepository.deleteAll(entities);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaReviewRepository.existsById(id);
    }

    @Override
    public Review getReferenceById(Long id) {
        JpaReviewEntity r = jpaReviewRepository.getReferenceById(id);
        return toDomain(r);
    }

    @Override
    public List<Review> findPendingEligibleByCongresso(Long congressoId, Date cutoff, Date now) {
        return jpaReviewRepository.findPendingEligibleByCongresso(congressoId, cutoff, now)
                .stream().map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> findByArticle_Congresso_Id(Long congressoId) {
        return jpaReviewRepository.findByArticle_Congresso_Id(congressoId)
                .stream().map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findReviewerIdsByArticle(Long articleId) {
        return List.copyOf(jpaReviewRepository.findReviewerIdsByArticle(articleId));
    }

    private Review toDomain(JpaReviewEntity entity) {
        if (entity == null) {
            return null;
        }

        Review review = new Review();

        review.setId(entity.getId());
        review.setComment(entity.getComment());
        review.setCreateAt(entity.getCreateAt());
        review.setScore(entity.getScore());

        if (entity.getEvaluation() != null) {
            Evaluation eval = new Evaluation();
            eval.setId(entity.getEvaluation().getId());
            eval.setFinalScore(entity.getEvaluation().getFinalScore());
            eval.setNumberOfReviews(entity.getEvaluation().getNumberOfReviews());
            review.setEvaluation(eval);
        }

        if (entity.getReviewer() != null) {
            User user = new User();
            user.setId(entity.getReviewer().getId());
            user.setUsernameUser(entity.getReviewer().getUsernameUser());
            user.setLogin(entity.getReviewer().getLogin());
            user.setWorkPlace(entity.getReviewer().getWorkPlace());
            user.setReviewer(entity.getReviewer().getIsReviewer());
            review.setReviewer(user);
        }

        if (entity.getArticle() != null) {
            Article article = new Article();
            article.setId(entity.getArticle().getId());
            article.setTitle(entity.getArticle().getTitle());
            review.setArticle(article);
        }

        return review;
    }
}
