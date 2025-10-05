package com.topavnbanco.artigos.domain.article;

import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.user.User;

import java.time.Instant;
import java.util.*;

public class Article {

    private Long id;

    private Set<String> knowledgeArea = new LinkedHashSet<>();

    private String title;

    private String description;

    private ReviewPerArticleStatus status;

    private String body;

    private ArticleFormat format;

    private Instant publishedAt;

    private Congresso congresso;

    private Set<User> articlesUsers = new HashSet<>();

    private Evaluation evaluation;

    private List<Review> reviews = new ArrayList<>();

    public Article() {
    }

    public Article(Long id, Set<String> knowledgeArea, String title, String description, ReviewPerArticleStatus status, Instant publishedAt, ArticleFormat format, String body, Set<User> articlesUsers, Evaluation evaluation, Congresso congresso, List<Review> reviews) {
        this.id = id;
        this.knowledgeArea = knowledgeArea;
        this.title = title;
        this.description = description;
        this.status = status;
        this.publishedAt = publishedAt;
        this.format = format;
        this.body = body;
        this.articlesUsers = articlesUsers;
        this.evaluation = evaluation;
        this.congresso = congresso;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setKnowledgeArea(Set<String> knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }

    public ReviewPerArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewPerArticleStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArticleFormat getFormat() {
        return format;
    }

    public void setFormat(ArticleFormat format) {
        this.format = format;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Congresso getCongresso() {
        return congresso;
    }

    public void setCongresso(Congresso congresso) {
        this.congresso = congresso;
    }

    public Set<User> getArticlesUsers() {
        return articlesUsers;
    }

    public void setArticlesUsers(Set<User> articlesUsers) {
        this.articlesUsers = articlesUsers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
