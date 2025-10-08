package com.topavnbanco.artigos.domain.role.repository;

import com.topavnbanco.artigos.domain.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleRepository {

    Role save(Role role);

    Optional<Role> findById(Long id);

    Optional<Role>  findByAuthority(String authority);

    Page<Role> findAll(Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);

    Role getReferenceById(Long id);
}
