package com.topavnbanco.artigos.adapters.outbound.entities;


import com.topavnbanco.artigos.domain.user.User;
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
    @OneToOne(fetch = FetchType.LAZY,
            cascade = { CascadeType.REMOVE },
            orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private JpaAddressEntity address;

    @Lob
    @Column(columnDefinition = "BLOB")
    @Setter
    private byte[] profileImage;

    @Setter
    @OneToOne(fetch = FetchType.LAZY,
            cascade = { CascadeType.REMOVE },
            orphanRemoval = true)    @JoinColumn(name = "card_id", nullable = false, unique = true)
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

    public JpaUserEntity(User user) {
        if (user != null) {
            this.id = user.getId();
            this.usernameUser = user.getUsernameUser();
            this.login = user.getLogin();
            this.password = user.getPassword();
            this.workPlace = user.getWorkPlace();
            this.membershipNumber = user.getMembershipNumber();
            this.isReviewer = user.getReviewer();
            this.profileImage = user.getProfileImage();

            this.address = (user.getAddress() != null) ? new JpaAddressEntity(user.getAddress()) : null;
            this.card    = (user.getCard()    != null) ? new JpaCardEntity(user.getCard())       : null;

            if (user.getCongresso() != null && user.getCongresso().getId() != null) {
                JpaCongressoEntity congressoRef = new JpaCongressoEntity();
                congressoRef.setId(user.getCongresso().getId());
                this.congresso = congressoRef;
            }

            this.roles = (user.getRoles() != null)
                    ? user.getRoles().stream()
                    .map(JpaRoleEntity::new)
                    .collect(java.util.stream.Collectors.toCollection(HashSet::new))
                    : new HashSet<>();
            this.userArticles = (user.getUserArticles() != null)
                    ? user.getUserArticles().stream()
                    .map(JpaArticleEntity::new)
                    .collect(java.util.stream.Collectors.toCollection(HashSet::new))
                    : new java.util.HashSet<>();
        }
    }

}
