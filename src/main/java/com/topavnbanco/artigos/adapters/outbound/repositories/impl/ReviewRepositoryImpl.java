package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaReviewEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaReviewRepository;
import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.review.repository.ReviewRepository;
import com.topavnbanco.artigos.infrastructure.mappers.ReviewMapper;
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

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public Review save(Review review) {
        JpaReviewEntity entity = reviewMapper.toEntity(review);
        entity = jpaReviewRepository.save(entity);
        return reviewMapper.toDomain(entity);
    }

    @Override
    public void saveAll(List<Review> reviews) {
        List<JpaReviewEntity> entities = reviews.stream()
                .map(reviewMapper::toEntity)
                .collect(Collectors.toList());
        jpaReviewRepository.saveAll(entities);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return jpaReviewRepository.findById(id)
                .map(reviewMapper::toDomain);
    }

    @Override
    public Page<Review> findAll(Specification<JpaReviewEntity> specification, Pageable pageable) {
        return jpaReviewRepository.findAll(specification, pageable)
                .map(reviewMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaReviewRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Review> reviews) {
        List<JpaReviewEntity> entities = reviews.stream()
                .map(reviewMapper::toEntity)
                .collect(Collectors.toList());
        jpaReviewRepository.deleteAll(entities);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaReviewRepository.existsById(id);
    }

    @Override
    public Review getReferenceById(Long id) {
        JpaReviewEntity entity = jpaReviewRepository.getReferenceById(id);
        return reviewMapper.toDomain(entity);
    }

    @Override
    public List<Review> findPendingEligibleByCongresso(Long congressoId, Date cutoff, Date now) {
        return jpaReviewRepository.findPendingEligibleByCongresso(congressoId, cutoff, now)
                .stream()
                .map(reviewMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> findByArticle_Congresso_Id(Long congressoId) {
        return jpaReviewRepository.findByArticle_Congresso_Id(congressoId)
                .stream()
                .map(reviewMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findReviewerIdsByArticle(Long articleId) {
        return List.copyOf(jpaReviewRepository.findReviewerIdsByArticle(articleId));
    }
}
