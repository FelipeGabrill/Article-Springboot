package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaUserEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaUserRepository;
import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.user.projections.UserDetailsProjection;
import com.topavnbanco.artigos.domain.user.repository.UserRepository;
import com.topavnbanco.artigos.infrastructure.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User save(User user) {
        JpaUserEntity entity = jpaUserRepository.save(userMapper.toEntity(user));
        return userMapper.toDomain(entity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(userMapper::toDomain);
    }

    @Override
    public List<User> findByCongressoId(Long congressoId) {
        return jpaUserRepository.findByCongressoId(congressoId).stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaUserRepository.existsById(id);
    }

    @Override
    public List<User> pickRandomEligible(Long articleId, List<Long> authorIds, int limit) {
        return jpaUserRepository.pickRandomEligible(articleId, authorIds, limit)
                .stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<User> findAllByCongressoId(Long congressoId, Pageable pageable) {
        return jpaUserRepository.findAllByCongressoId(congressoId, pageable)
                .map(userMapper::toDomain);
    }

    @Override
    public User getReferenceById(Long id) {
        JpaUserEntity entity = jpaUserRepository.getReferenceById(id);
        return userMapper.toDomain(entity);
    }

    @Override
    public Optional<User> pickOneRandomEligible(Long articleId, Set<Long> usedReviewerIds) {
        return jpaUserRepository.pickOneRandomEligible(articleId, usedReviewerIds)
                .map(userMapper::toDomain);
    }

    @Override
    public Set<Long> findExistingIds(Set<Long> ids) {
        return jpaUserRepository.findExistingIds(ids);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return jpaUserRepository.findByLogin(login)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByLogin(String login) {
        return jpaUserRepository.existsByLogin(login);
    }

    @Override
    public Page<User> findByLoginIgnoreCaseContaining(String login, Pageable pageable) {
        return jpaUserRepository.findByLoginIgnoreCaseContaining(login, pageable)
                .map(userMapper::toDomain);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return jpaUserRepository.findAll(pageable)
                .map(userMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public List<UserDetailsProjection> searchUserAndRolesByLogin(String login) {
        return jpaUserRepository.searchUserAndRolesByLogin(login);
    }
}
