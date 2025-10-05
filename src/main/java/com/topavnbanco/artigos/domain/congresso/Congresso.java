package com.topavnbanco.artigos.domain.congresso;

import com.topavnbanco.artigos.domain.congresso.enuns.CongressoModality;
import com.topavnbanco.artigos.domain.user.User;

import java.util.*;

public class Congresso {

    private Long id;

    private byte[] imageThumbnail;

    private String name;

    private Set<String> knowledgeArea = new LinkedHashSet<>();

    private String description;

    private String descriptionTitle;

    private CongressoModality congressoModality;

    private Date startDate;

    private Date endDate;

    private String place;

    private Date submissionDeadline;

    private Date reviewDeadline;

    private Integer maxReviewsPerArticle;

    private Integer minReviewsPerArticle;

    private List<User> user = new ArrayList<>();

    public Congresso() {
    }

    public Congresso(Long id, String name, byte[] imageThumbnail, Set<String> knowledgeArea, String description, CongressoModality congressoModality, String descriptionTitle, Date startDate, Date endDate, Date reviewDeadline, Integer maxReviewsPerArticle, String place, Date submissionDeadline, List<User> user, Integer minReviewsPerArticle) {
        this.id = id;
        this.name = name;
        this.imageThumbnail = imageThumbnail;
        this.knowledgeArea = knowledgeArea;
        this.description = description;
        this.congressoModality = congressoModality;
        this.descriptionTitle = descriptionTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reviewDeadline = reviewDeadline;
        this.maxReviewsPerArticle = maxReviewsPerArticle;
        this.place = place;
        this.submissionDeadline = submissionDeadline;
        this.user = user;
        this.minReviewsPerArticle = minReviewsPerArticle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(byte[] imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionTitle() {
        return descriptionTitle;
    }

    public void setDescriptionTitle(String descriptionTitle) {
        this.descriptionTitle = descriptionTitle;
    }

    public Set<String> getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setKnowledgeArea(Set<String> knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }

    public CongressoModality getCongressoModality() {
        return congressoModality;
    }

    public void setCongressoModality(CongressoModality congressoModality) {
        this.congressoModality = congressoModality;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSubmissionDeadline() {
        return submissionDeadline;
    }

    public void setSubmissionDeadline(Date submissionDeadline) {
        this.submissionDeadline = submissionDeadline;
    }

    public Date getReviewDeadline() {
        return reviewDeadline;
    }

    public void setReviewDeadline(Date reviewDeadline) {
        this.reviewDeadline = reviewDeadline;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getMaxReviewsPerArticle() {
        return maxReviewsPerArticle;
    }

    public void setMaxReviewsPerArticle(Integer maxReviewsPerArticle) {
        this.maxReviewsPerArticle = maxReviewsPerArticle;
    }

    public Integer getMinReviewsPerArticle() {
        return minReviewsPerArticle;
    }

    public void setMinReviewsPerArticle(Integer minReviewsPerArticle) {
        this.minReviewsPerArticle = minReviewsPerArticle;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
