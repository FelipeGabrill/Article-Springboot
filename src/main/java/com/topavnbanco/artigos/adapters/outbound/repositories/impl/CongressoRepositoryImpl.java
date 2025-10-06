package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCongressoEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaCongressoRepository;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.congresso.repository.CongressoRepository;
import com.topavnbanco.artigos.infrastructure.mappers.CongressoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CongressoRepositoryImpl implements CongressoRepository {

    @Autowired
    private JpaCongressoRepository jpaCongressoRepository;

    @Autowired
    private CongressoMapper congressoMapper;

    @Override
    public Congresso save(Congresso congresso) {
        JpaCongressoEntity entity = congressoMapper.toEntity(congresso);
        entity = jpaCongressoRepository.save(entity);
        return congressoMapper.toDomain(entity);
    }

    @Override
    public Optional<Congresso> findById(Long id) {
        return jpaCongressoRepository.findById(id)
                .map(congressoMapper::toDomain);
    }

    @Override
    public Page<Congresso> findAll(Specification<JpaCongressoEntity> specification, Pageable pageable) {
        return jpaCongressoRepository.findAll(specification, pageable)
                .map(congressoMapper::toDomain);
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
        JpaCongressoEntity entity = jpaCongressoRepository.getReferenceById(id);
        return congressoMapper.toDomain(entity);
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
