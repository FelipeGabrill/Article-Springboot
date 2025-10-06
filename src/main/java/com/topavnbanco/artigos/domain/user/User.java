package com.topavnbanco.artigos.domain.user;

import com.topavnbanco.artigos.domain.address.Address;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.card.Card;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.role.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails {

    private Long id;

    private String usernameUser;

    private String login;

    private String password;

    private String workPlace;

    private UUID membershipNumber;

    private Boolean isReviewer;

    private Address address;

    private byte[] profileImage;

    private Card card;

    private Congresso congresso;

    private Set<Role> roles = new HashSet<>();

    private Set<Article> userArticles = new HashSet<>();

    public User() {
    }

    public User(Long id, String usernameUser, String password, String login, String workPlace, UUID membershipNumber, Boolean isReviewer, Address address, Card card, byte[] profileImage, Congresso congresso, Set<Role> roles, Set<Article> userArticles) {
        this.id = id;
        this.usernameUser = usernameUser;
        this.password = password;
        this.login = login;
        this.workPlace = workPlace;
        this.membershipNumber = membershipNumber;
        this.isReviewer = isReviewer;
        this.address = address;
        this.card = card;
        this.profileImage = profileImage;
        this.congresso = congresso;
        this.roles = roles;
        this.userArticles = userArticles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean hasRole(String roleName) {
        for(Role role : roles) {
            if(role.getAuthority().equals(roleName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        this.usernameUser = usernameUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public UUID getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(UUID membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public Boolean getReviewer() {
        return isReviewer;
    }

    public void setReviewer(Boolean reviewer) {
        isReviewer = reviewer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public Congresso getCongresso() {
        return congresso;
    }

    public void setCongresso(Congresso congresso) {
        this.congresso = congresso;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Set<Article> getUserArticles() {
        return userArticles;
    }

    public void setUserArticles(Set<Article> userArticles) {
        this.userArticles = userArticles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

}