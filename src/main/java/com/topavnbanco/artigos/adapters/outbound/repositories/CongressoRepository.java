package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.domain.congresso.Congresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CongressoRepository extends JpaRepository<Congresso, Long>, JpaSpecificationExecutor<Congresso> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
