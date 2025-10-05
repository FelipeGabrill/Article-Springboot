package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaRoleRepository extends JpaRepository<JpaRoleEntity, Long> {
}
