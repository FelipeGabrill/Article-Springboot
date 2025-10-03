package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.projections.UserDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
			SELECT tb_user.login AS login, tb_user.password, tb_role.id AS 
				roleId, tb_role.authority 
				FROM tb_user INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id 
				INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id 
				WHERE tb_user.login = :login
			""")
    List<UserDetailsProjection> searchUserAndRolesByLogin(String login);

    @EntityGraph(attributePaths = "userArticles")
    Optional<User> findById(Long id);

    @Query(value = """
        SELECT u.* 
        FROM tb_user u
        WHERE u.is_reviewer = TRUE
          AND u.id NOT IN (:authorIds)              
          AND NOT EXISTS (                         
                SELECT 1 FROM tb_review r 
                WHERE r.article_id = :articleId 
                  AND r.reviewer_id = u.id
          )
        ORDER BY RAND()
        LIMIT :limit
        """, nativeQuery = true)
    List<User> pickRandomEligible(
            @Param("articleId") Long articleId,
            @Param("authorIds") List<Long> authorIds,
            @Param("limit") int limit
    );

    @Query(value = """
    SELECT u.*
    FROM tb_user u
    WHERE u.is_reviewer = TRUE
      AND u.id NOT IN (:usedReviewerIds)       
      AND NOT EXISTS (SELECT 1 FROM tb_articles_users au
                      WHERE au.article_id = :articleId
                        AND au.user_id = u.id)
    ORDER BY RAND()
    LIMIT 1
    """, nativeQuery = true)
    Optional<User> pickOneRandomEligible(
            @Param("articleId") Long articleId,
            @Param("usedReviewerIds") Set<Long> usedReviewerIds
    );


    @Query("select u.id from User u where u.id in :ids")
    Set<Long> findExistingIds(@Param("ids") Set<Long> ids);

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    Page<User> findByLoginIgnoreCaseContaining(String login, Pageable pageable);

}
