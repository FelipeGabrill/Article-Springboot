package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JpaRoleRepository extends JpaRepository<JpaRoleEntity, Long> {

    Optional<JpaRoleEntity> findByAuthority(String authority);
}
