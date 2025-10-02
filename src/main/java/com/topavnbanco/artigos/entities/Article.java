package com.topavnbanco.artigos.entities;

import com.topavnbanco.artigos.entities.enuns.ArticleFormat;
import com.topavnbanco.artigos.entities.enuns.ReviewPerArticleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_article")
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {

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
    private String body;

    @Enumerated(EnumType.STRING)
    private ArticleFormat format;

    private Instant publishedAt;

    @ManyToOne
    @JoinColumn(name = "congresso_id")
    private Congresso congresso;

    @ManyToMany
    @JoinTable(name = "tb_articles_users",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> articlesUsers = new HashSet<>();

    @OneToOne(mappedBy = "article", fetch = FetchType.LAZY)
    private Evaluation evaluation;

    @OneToMany(mappedBy = "article")
    private List<Review> reviews = new ArrayList<>();

    public Article() {
    }
}
