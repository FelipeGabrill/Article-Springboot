package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaRoleEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaRoleRepository;
import com.topavnbanco.artigos.domain.role.Role;
import com.topavnbanco.artigos.domain.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private JpaRoleRepository jpaRoleRepository;

    @Override
    public Role save(Role role) {
        JpaRoleEntity r = jpaRoleRepository.save(new JpaRoleEntity(role));
        return new Role(
                r.getId(),
                r.getAuthority()
        );
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpaRoleRepository.findById(id)
                .map(r -> new Role(
                        r.getId(),
                        r.getAuthority()
                ));
    }


    @Override
    public Page<Role> findAll(Pageable pageable) {
        return jpaRoleRepository.findAll(pageable) .map(r -> new Role(
                r.getId(),
                r.getAuthority()
        ));
    }

    @Override
    public void deleteById(Long id) {
        jpaRoleRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRoleRepository.existsById(id);
    }

    @Override
    public Role getReferenceById(Long id) {
        JpaRoleEntity r = jpaRoleRepository.getReferenceById(id);
        return new Role(
                r.getId(),
                r.getAuthority());
    }
}
