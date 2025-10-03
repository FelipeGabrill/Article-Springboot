package com.topavnbanco.artigos.domain.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tb_role")
@AllArgsConstructor
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements GrantedAuthority {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    private String authority;

    public Role() {
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
