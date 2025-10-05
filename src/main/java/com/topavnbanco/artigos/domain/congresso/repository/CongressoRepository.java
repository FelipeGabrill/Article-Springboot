package com.topavnbanco.artigos.domain.congresso.repository;


import com.topavnbanco.artigos.adapters.outbound.entities.JpaCongressoEntity;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface CongressoRepository {

    Congresso save(Congresso congresso);

    Optional<Congresso> findById(Long id);

    Page<Congresso> findAll(Specification<JpaCongressoEntity> specification, Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);

    Congresso getReferenceById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
