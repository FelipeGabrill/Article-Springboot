package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaAddressRepository extends JpaRepository<JpaAddressEntity, Long> {
}
