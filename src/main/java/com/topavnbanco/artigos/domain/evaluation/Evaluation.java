package com.topavnbanco.artigos.domain.evaluation;

import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.article.Article;

import java.util.ArrayList;
import java.util.List;

public class Evaluation {

    private Long id;

    private Double finalScore;

    private Integer numberOfReviews;

    private Article article;

    private List<Review> reviews = new ArrayList<>();

    public Evaluation() {
    }

    public Evaluation(Long id, Double finalScore, Integer numberOfReviews, Article article, List<Review> reviews) {
        this.id = id;
        this.finalScore = finalScore;
        this.numberOfReviews = numberOfReviews;
        this.article = article;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(Integer numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
