package com.topavnbanco.artigos.repositories;

import com.topavnbanco.artigos.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByScoreIsNullAndCreateAtBefore(Date cutoff);

    List<Review> findByArticle_Congresso_Id(Long congressoId);

    @Query("SELECT r.reviewer.id FROM Review r WHERE r.article.id = :articleId")
    List<Long> findReviewerIdsByArticle(@Param("articleId") Long articleId);
}
