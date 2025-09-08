package com.topavnbanco.artigos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tb_user")
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter
    private Long id;

    @Setter
    private String usernameUser;

    @Column(unique = true)
    @Setter
    private String login;

    @Setter
    private String password;

    @Setter
    private String workPlace;

    @Column(name = "membership_number", nullable = false, unique = true, updatable = false)
    private UUID membershipNumber;

    @Setter
    private Boolean isReviewer;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private Address address;

    @Lob
    @Column(columnDefinition = "BLOB")
    @Setter
    private byte[] profileImage;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", nullable = false, unique = true)
    private Card card;

    @Setter
    @ManyToOne
    @JoinColumn(name = "congresso_id")
    private Congresso congresso;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Setter
    @ManyToMany(mappedBy = "articlesUsers")
    private Set<Article> userArticles = new HashSet<>();

    public User() {
    }

    @PrePersist
    private void prePersist() {
        if (membershipNumber == null) {
            membershipNumber = UUID.randomUUID();
        }
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

}
