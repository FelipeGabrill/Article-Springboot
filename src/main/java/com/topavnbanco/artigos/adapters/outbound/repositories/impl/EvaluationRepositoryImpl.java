package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaEvaluationEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaEvaluationRepository;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.domain.evaluation.repository.EvaluationRepository;
import com.topavnbanco.artigos.infrastructure.mappers.EvaluationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EvaluationRepositoryImpl implements EvaluationRepository {

    @Autowired
    private JpaEvaluationRepository jpaEvaluationRepository;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Override
    public Evaluation save(Evaluation evaluation) {
        JpaEvaluationEntity entity = evaluationMapper.toEntity(evaluation);
        entity = jpaEvaluationRepository.save(entity);
        return evaluationMapper.toDomain(entity);
    }

    @Override
    public Optional<Evaluation> findByArticle_Id(Long articleId) {
        return jpaEvaluationRepository.findByArticle_Id(articleId)
                .map(evaluationMapper::toDomain);
    }

    @Override
    public Optional<Evaluation> findById(Long id) {
        return jpaEvaluationRepository.findById(id)
                .map(evaluationMapper::toDomain);
    }

    @Override
    public Page<Evaluation> findAll(Pageable pageable) {
        return jpaEvaluationRepository.findAll(pageable)
                .map(evaluationMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaEvaluationRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaEvaluationRepository.existsById(id);
    }

    @Override
    public Evaluation getReferenceById(Long id) {
        JpaEvaluationEntity entity = jpaEvaluationRepository.getReferenceById(id);
        return evaluationMapper.toDomain(entity);
    }
}
