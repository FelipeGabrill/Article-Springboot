package com.topavnbanco.artigos.domain.review;

import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;

import java.util.Date;

public class Review {

    private Long id;

    private String comment;

    private Integer score;

    private Date createAt;

    private Article article;

    private User reviewer;

    private Evaluation evaluation;

    public Review() {
    }

    public Review(Long id, String comment, Date createAt, Integer score, User reviewer, Article article, Evaluation evaluation) {
        this.id = id;
        this.comment = comment;
        this.createAt = createAt;
        this.score = score;
        this.reviewer = reviewer;
        this.article = article;
        this.evaluation = evaluation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
