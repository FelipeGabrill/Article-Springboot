package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaCardRepository extends JpaRepository<JpaCardEntity, Long> {
}
