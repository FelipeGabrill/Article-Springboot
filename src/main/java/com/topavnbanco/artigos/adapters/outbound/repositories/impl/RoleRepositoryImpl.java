package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaRoleEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaRoleRepository;
import com.topavnbanco.artigos.domain.role.Role;
import com.topavnbanco.artigos.domain.role.repository.RoleRepository;
import com.topavnbanco.artigos.infrastructure.mappers.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private JpaRoleRepository jpaRoleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role save(Role role) {
        JpaRoleEntity entity = roleMapper.toEntity(role);
        entity = jpaRoleRepository.save(entity);
        return roleMapper.toDomain(entity);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpaRoleRepository.findById(id)
                .map(roleMapper::toDomain);
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return jpaRoleRepository.findAll(pageable)
                .map(roleMapper::toDomain);
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
        JpaRoleEntity entity = jpaRoleRepository.getReferenceById(id);
        return roleMapper.toDomain(entity);
    }
}
