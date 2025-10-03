package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    @Query("""
       SELECT r
         FROM Review r
         JOIN r.article a
         JOIN a.congresso c
        WHERE r.score IS NULL
          AND r.createAt < :cutoff
          AND a.status = com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus.PENDING
          AND c.id = :congressoId
          AND c.reviewDeadline > :now
    """)
    List<Review> findPendingEligibleByCongresso(@Param("congressoId") Long congressoId,
                                                @Param("cutoff")      Date cutoff,
                                                @Param("now")         Date now);

    List<Review> findByArticle_Congresso_Id(Long congressoId);

    @Query("SELECT r.reviewer.id FROM Review r WHERE r.article.id = :articleId")
    List<Long> findReviewerIdsByArticle(@Param("articleId") Long articleId);

}
