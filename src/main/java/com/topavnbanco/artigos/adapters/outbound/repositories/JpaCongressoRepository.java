package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCongressoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface JpaCongressoRepository extends JpaRepository<JpaCongressoEntity, Long>, JpaSpecificationExecutor<JpaCongressoEntity> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
