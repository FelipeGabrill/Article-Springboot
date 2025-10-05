package com.topavnbanco.artigos.adapters.outbound.entities;

import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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

    public JpaArticleEntity(Article article) {
        if (article != null) {
            this.id = article.getId();
            this.knowledgeArea = article.getKnowledgeArea() != null
                    ? new LinkedHashSet<>(article.getKnowledgeArea())
                    : new LinkedHashSet<>();
            this.title = article.getTitle();
            this.description = article.getDescription();
            this.status = article.getStatus();
            this.body = article.getBody();
            this.format = article.getFormat();
            this.publishedAt = article.getPublishedAt();
            this.congresso = article.getCongresso() != null ? new JpaCongressoEntity(article.getCongresso()) : null;
            this.articlesUsers = article.getArticlesUsers() != null
                    ? article.getArticlesUsers().stream()
                    .map(JpaUserEntity::new)
                    .collect(Collectors.toCollection(HashSet::new))
                    : new HashSet<>();
            if (article.getEvaluation() != null && article.getEvaluation().getId() != null) {
                JpaEvaluationEntity evalRef = new JpaEvaluationEntity();
                evalRef.setId(article.getEvaluation().getId());
                this.evaluation = evalRef;
            } else {
                this.evaluation = null;
            }

            if (article.getReviews() != null) {
                this.reviews = article.getReviews().stream()
                        .filter(r -> r != null && r.getId() != null)
                        .map(r -> {
                            JpaReviewEntity ref = new JpaReviewEntity();
                            ref.setId(r.getId());
                            return ref;
                        })
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        }
    }
}
