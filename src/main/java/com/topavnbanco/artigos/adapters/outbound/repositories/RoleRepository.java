package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
