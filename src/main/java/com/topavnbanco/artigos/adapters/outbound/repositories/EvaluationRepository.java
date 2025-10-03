package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
