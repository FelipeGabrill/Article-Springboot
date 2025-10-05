package com.topavnbanco.artigos.domain.user.repository;

import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.user.projections.UserDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    User save(User user);

    Page<User> findAll(Pageable pageable);

    void deleteById(Long id);

    List<UserDetailsProjection> searchUserAndRolesByLogin(String login);

    Optional<User> findById(Long id);

    boolean existsById(Long id);

    List<User> pickRandomEligible(
            Long articleId,
            List<Long> authorIds,
            int limit
    );

    User getReferenceById(Long id);

    Optional<User> pickOneRandomEligible(
            Long articleId,
            Set<Long> usedReviewerIds
    );

    Set<Long> findExistingIds(Set<Long> ids);

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    Page<User> findByLoginIgnoreCaseContaining(String login, Pageable pageable);

}
