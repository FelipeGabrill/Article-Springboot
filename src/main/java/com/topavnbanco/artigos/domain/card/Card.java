package com.topavnbanco.artigos.domain.card;


import java.util.Date;

public class Card {

    private Long id;

    private String number;

    private Date expired;

    private Integer cvv;

    public Card() {
    }

    public Card(Long id, String number, Date expired, Integer cvv) {
        this.id = id;
        this.number = number;
        this.expired = expired;
        this.cvv = cvv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
}
