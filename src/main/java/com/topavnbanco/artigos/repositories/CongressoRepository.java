package com.topavnbanco.artigos.repositories;

import com.topavnbanco.artigos.entities.Congresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


@Repository
public interface CongressoRepository extends JpaRepository<Congresso, Long> {

    List<Congresso> findByReviewDeadlineGreaterThanEqual(Instant now);

}
