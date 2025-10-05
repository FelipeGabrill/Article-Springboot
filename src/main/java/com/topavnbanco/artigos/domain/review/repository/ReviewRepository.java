package com.topavnbanco.artigos.domain.review.repository;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaReviewEntity;
import com.topavnbanco.artigos.domain.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    Review save(Review review);

    void saveAll(List<Review> review);

    Optional<Review> findById(Long id);

    Page<Review> findAll(Specification<JpaReviewEntity> specification, Pageable pageable);

    void deleteById(Long id);

    void deleteAll(List<Review> reviews);

    boolean existsById(Long id);

    Review getReferenceById(Long id);

    List<Review> findPendingEligibleByCongresso(Long congressoId,
                                                Date cutoff,
                                                Date now);

    List<Review> findByArticle_Congresso_Id(Long congressoId);

    List<Long> findReviewerIdsByArticle(Long articleId);

}
