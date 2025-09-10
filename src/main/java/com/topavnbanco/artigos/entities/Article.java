package com.topavnbanco.artigos.entities;

import com.topavnbanco.artigos.entities.enuns.ArticleFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String description;

    private Boolean isValid;

    @Lob
    private String body;

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

    @OneToMany(mappedBy = "article")
    private List<Review> review = new ArrayList<>();

    public Article() {
    }
}
