package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCongressoEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaCongressoRepository;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.congresso.repository.CongressoRepository;
import com.topavnbanco.artigos.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CongressoRepositoryImpl implements CongressoRepository {

    @Autowired
    private JpaCongressoRepository jpaCongressoRepository;

    @Override
    public Congresso save(Congresso congresso) {
        JpaCongressoEntity c = jpaCongressoRepository.save(new JpaCongressoEntity(congresso));
        return new Congresso(
                c.getId(),
                c.getName(),
                c.getImageThumbnail(),
                c.getKnowledgeArea(),
                c.getDescription(),
                c.getCongressoModality(),
                c.getDescriptionTitle(),
                c.getStartDate(),
                c.getEndDate(),
                c.getReviewDeadline(),
                c.getMaxReviewsPerArticle(),
                c.getPlace(),
                c.getSubmissionDeadline(),
                c.getUser() != null
                        ? c.getUser().stream()
                        .map(r -> new User(
                                r.getId(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                        ))
                        .collect(Collectors.toList())
                        : new ArrayList<>(),
                c.getMinReviewsPerArticle()
        );
    }

    @Override
    public Optional<Congresso> findById(Long id) {
        return jpaCongressoRepository.findById(id)
                .map(c -> new Congresso(
                        c.getId(),
                        c.getName(),
                        c.getImageThumbnail(),
                        c.getKnowledgeArea(),
                        c.getDescription(),
                        c.getCongressoModality(),
                        c.getDescriptionTitle(),
                        c.getStartDate(),
                        c.getEndDate(),
                        c.getReviewDeadline(),
                        c.getMaxReviewsPerArticle(),
                        c.getPlace(),
                        c.getSubmissionDeadline(),
                        null,
                        c.getMinReviewsPerArticle()
                ));
    }


    @Override
    public Page<Congresso> findAll(Specification<JpaCongressoEntity> specification, Pageable pageable) {
        return jpaCongressoRepository
                .findAll(specification, pageable)
                .map(c -> new Congresso(
                        c.getId(),
                        c.getName(),
                        c.getImageThumbnail(),
                        c.getKnowledgeArea(),
                        c.getDescription(),
                        c.getCongressoModality(),
                        c.getDescriptionTitle(),
                        c.getStartDate(),
                        c.getEndDate(),
                        c.getReviewDeadline(),
                        c.getMaxReviewsPerArticle(),
                        c.getPlace(),
                        c.getSubmissionDeadline(),
                        null,
                        c.getMinReviewsPerArticle()
                ));
    }

    @Override
    public void deleteById(Long id) {
        jpaCongressoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaCongressoRepository.existsById(id);
    }

    @Override
    public Congresso getReferenceById(Long id) {
        JpaCongressoEntity c = jpaCongressoRepository.getReferenceById(id);
        return new Congresso(c.getId(),
                c.getName(),
                c.getImageThumbnail(),
                c.getKnowledgeArea(),
                c.getDescription(),
                c.getCongressoModality(),
                c.getDescriptionTitle(),
                c.getStartDate(),
                c.getEndDate(),
                c.getReviewDeadline(),
                c.getMaxReviewsPerArticle(),
                c.getPlace(),
                c.getSubmissionDeadline(),
                null,
                c.getMinReviewsPerArticle());
    }

    @Override
    public boolean existsByName(String name) {
        return jpaCongressoRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return jpaCongressoRepository.existsByNameAndIdNot(name, id);
    }
}
