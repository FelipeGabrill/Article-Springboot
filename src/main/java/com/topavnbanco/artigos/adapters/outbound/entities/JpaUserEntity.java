package com.topavnbanco.artigos.adapters.outbound.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JpaUserEntity {

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
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private JpaAddressEntity address;

    @Lob
    @Column(columnDefinition = "BLOB")
    @Setter
    private byte[] profileImage;

    @Setter
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "card_id", nullable = false, unique = true)
    private JpaCardEntity card;

    @Setter
    @ManyToOne
    @JoinColumn(name = "congresso_id")
    private JpaCongressoEntity congresso;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<JpaRoleEntity> roles = new HashSet<>();

    @Setter
    @ManyToMany(mappedBy = "articlesUsers")
    private Set<JpaArticleEntity> userArticles = new HashSet<>();

    @PrePersist
    private void prePersist() {
        if (membershipNumber == null) {
            membershipNumber = UUID.randomUUID();
        }
    }
}