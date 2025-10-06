package com.topavnbanco.artigos.adapters.outbound.entities;

import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_article")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JpaArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ElementCollection
    private Set<String> knowledgeArea = new LinkedHashSet<>();

    @Column(unique = true)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private ReviewPerArticleStatus status;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String body;

    @Enumerated(EnumType.STRING)
    private ArticleFormat format;

    private Instant publishedAt;

    @ManyToOne
    @JoinColumn(name = "congresso_id")
    private JpaCongressoEntity congresso;

    @ManyToMany
    @JoinTable(name = "tb_articles_users",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<JpaUserEntity> articlesUsers = new HashSet<>();

    @OneToOne(mappedBy = "article", fetch = FetchType.LAZY)
    private JpaEvaluationEntity evaluation;

    @OneToMany(mappedBy = "article")
    private List<JpaReviewEntity> reviews = new ArrayList<>();
}
