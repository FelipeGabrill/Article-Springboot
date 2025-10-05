package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaUserEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaUserRepository;
import com.topavnbanco.artigos.domain.address.Address;
import com.topavnbanco.artigos.domain.card.Card;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.role.Role;
import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.user.projections.UserDetailsProjection;
import com.topavnbanco.artigos.domain.user.repository.UserRepository;
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

    @Override
    public User save(User user) {
        JpaUserEntity u = jpaUserRepository.save(new JpaUserEntity(user));
        return new User(
                u.getId(),
                u.getUsernameUser(),
                u.getPassword(),
                u.getLogin(),
                u.getWorkPlace(),
                u.getMembershipNumber(),
                u.getIsReviewer(),
                new Address(
                        u.getAddress().getId(),
                        u.getAddress().getStreet(),
                        u.getAddress().getNumber(),
                        u.getAddress().getComplement(),
                        u.getAddress().getCity(),
                        u.getAddress().getState(),
                        u.getAddress().getZipCode(),
                        u.getAddress().getCountry()
                ),
                new Card(
                        u.getCard().getId(),
                        u.getCard().getNumber(),
                        u.getCard().getExpired(),
                        u.getCard().getCvv()
                ),
                u.getProfileImage(),
                new Congresso(u.getCongresso().getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                u.getRoles() != null
                        ? u.getRoles().stream()
                        .map(r -> new Role(
                                r.getId(),
                                r.getAuthority()
                        ))
                        .collect(Collectors.toCollection(HashSet::new))
                        : new HashSet<>(),
                new HashSet<>()
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map( u -> new User(
                u.getId(),
                u.getUsernameUser(),
                u.getPassword(),
                u.getLogin(),
                u.getWorkPlace(),
                u.getMembershipNumber(),
                u.getIsReviewer(),
                new Address(
                        u.getAddress().getId(),
                        u.getAddress().getStreet(),
                        u.getAddress().getNumber(),
                        u.getAddress().getComplement(),
                        u.getAddress().getCity(),
                        u.getAddress().getState(),
                        u.getAddress().getZipCode(),
                        u.getAddress().getCountry()
                ),
                new Card(
                        u.getCard().getId(),
                        u.getCard().getNumber(),
                        u.getCard().getExpired(),
                        u.getCard().getCvv()
                ),
                u.getProfileImage(),
                new Congresso(u.getCongresso().getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                u.getRoles() != null
                        ? u.getRoles().stream()
                        .map(r -> new Role(
                                r.getId(),
                                r.getAuthority()
                        ))
                        .collect(Collectors.toCollection(HashSet::new))
                        : new HashSet<>(),
                new HashSet<>()
        ));
    }

    @Override
    public boolean existsById(Long id) {
        return jpaUserRepository.existsById(id);
    }

    @Override
    public List<User> pickRandomEligible(Long articleId, List<Long> authorIds, int limit) {
        return jpaUserRepository.pickRandomEligible(articleId, authorIds, limit)
                .stream()
                .map( u -> new User(
                u.getId(),
                u.getUsernameUser(),
                u.getPassword(),
                u.getLogin(),
                u.getWorkPlace(),
                u.getMembershipNumber(),
                u.getIsReviewer(),
                new Address(u.getAddress().getId(), null, null, null, null, null, null, null),
                new Card(u.getCard().getId(), null, null, null),
                u.getProfileImage(),
                new Congresso(u.getCongresso().getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                        u.getRoles() != null
                                ? u.getRoles().stream()
                                .map(r -> new Role(
                                        r.getId(),
                                        r.getAuthority()
                                ))
                                .collect(Collectors.toCollection(HashSet::new))
                                : new HashSet<>(),
                new HashSet<>()
        )).collect(Collectors.toList());
    }

    @Override
    public User getReferenceById(Long id) {
        JpaUserEntity u = jpaUserRepository.getReferenceById(id);
        return new User(
                u.getId(),
                u.getUsernameUser(),
                u.getPassword(),
                u.getLogin(),
                u.getWorkPlace(),
                u.getMembershipNumber(),
                u.getIsReviewer(),
                new Address(
                        u.getAddress().getId(),
                        u.getAddress().getStreet(),
                        u.getAddress().getNumber(),
                        u.getAddress().getComplement(),
                        u.getAddress().getCity(),
                        u.getAddress().getState(),
                        u.getAddress().getZipCode(),
                        u.getAddress().getCountry()
                ),
                new Card(
                        u.getCard().getId(),
                        u.getCard().getNumber(),
                        u.getCard().getExpired(),
                        u.getCard().getCvv()
                ),
                u.getProfileImage(),
                new Congresso(u.getCongresso().getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                u.getRoles() != null
                        ? u.getRoles().stream()
                        .map(r -> new Role(
                                r.getId(),
                                r.getAuthority()
                        ))
                        .collect(Collectors.toCollection(HashSet::new))
                        : new HashSet<>(),
                new HashSet<>()
        );
    }

    @Override
    public Optional<User> pickOneRandomEligible(Long articleId, Set<Long> usedReviewerIds) {
        return jpaUserRepository.pickOneRandomEligible(articleId, usedReviewerIds)
                .map(u -> new User(
                        u.getId(),
                        u.getUsernameUser(),
                        u.getPassword(),
                        u.getLogin(),
                        u.getWorkPlace(),
                        u.getMembershipNumber(),
                        u.getIsReviewer(),
                        new Address(u.getAddress().getId(), null, null, null, null, null, null, null),
                        new Card(u.getCard().getId(), null, null, null),
                        u.getProfileImage(),
                        new Congresso(u.getCongresso().getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                        u.getRoles() != null
                                ? u.getRoles().stream()
                                .map(r -> new Role(
                                        r.getId(),
                                        r.getAuthority()
                                ))
                                .collect(Collectors.toCollection(HashSet::new))
                                : new HashSet<>(),
                        new HashSet<>()
                ));
    }

    @Override
    public Set<Long> findExistingIds(Set<Long> ids) {
        return jpaUserRepository.findExistingIds(ids);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return jpaUserRepository.findByLogin(login)
                .map(u -> new User(
                        u.getId(),
                        u.getUsernameUser(),
                        u.getPassword(),
                        u.getLogin(),
                        u.getWorkPlace(),
                        u.getMembershipNumber(),
                        u.getIsReviewer(),
                        new Address(
                            u.getAddress().getId(),
                            u.getAddress().getStreet(),
                            u.getAddress().getNumber(),
                            u.getAddress().getComplement(),
                            u.getAddress().getCity(),
                            u.getAddress().getState(),
                            u.getAddress().getZipCode(),
                            u.getAddress().getCountry()
                        ),
                        new Card(
                                u.getCard().getId(),
                                u.getCard().getNumber(),
                                u.getCard().getExpired(),
                                u.getCard().getCvv()
                        ),
                        u.getProfileImage(),
                        new Congresso(u.getCongresso().getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                        u.getRoles() != null
                                ? u.getRoles().stream()
                                .map(r -> new Role(
                                        r.getId(),
                                        r.getAuthority()
                                ))
                                .collect(Collectors.toCollection(HashSet::new))
                                : new HashSet<>(),
                        new HashSet<>()
                ));
    }

    @Override
    public boolean existsByLogin(String login) {
        return jpaUserRepository.existsByLogin(login);
    }

    @Override
    public Page<User> findByLoginIgnoreCaseContaining(String login, Pageable pageable) {
        return jpaUserRepository.findByLoginIgnoreCaseContaining(login, pageable)
                .map(u -> new User(
                        u.getId(),
                        u.getUsernameUser(),
                        u.getPassword(),
                        u.getLogin(),
                        u.getWorkPlace(),
                        u.getMembershipNumber(),
                        u.getIsReviewer(),
                        new Address(u.getAddress().getId(), null, null, null, null, null, null, null),
                        new Card(u.getCard().getId(), null, null, null),
                        u.getProfileImage(),
                        new Congresso(u.getCongresso().getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                        u.getRoles() != null
                                ? u.getRoles().stream()
                                .map(r -> new Role(
                                        r.getId(),
                                        r.getAuthority()
                                ))
                                .collect(Collectors.toCollection(HashSet::new))
                                : new HashSet<>(),
                        new HashSet<>()
                ));
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return jpaUserRepository.findAll(pageable).map( u -> new User(
                u.getId(),
                u.getUsernameUser(),
                u.getPassword(),
                u.getLogin(),
                u.getWorkPlace(),
                u.getMembershipNumber(),
                u.getIsReviewer(),
                new Address(u.getAddress().getId(), null, null, null, null, null, null, null),
                new Card(u.getCard().getId(), null, null, null),
                u.getProfileImage(),
                new Congresso(u.getCongresso().getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                u.getRoles() != null
                        ? u.getRoles().stream()
                        .map(r -> new Role(
                                r.getId(),
                                r.getAuthority()
                        ))
                        .collect(Collectors.toCollection(HashSet::new))
                        : new HashSet<>(),
                new HashSet<>()
        ));
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
