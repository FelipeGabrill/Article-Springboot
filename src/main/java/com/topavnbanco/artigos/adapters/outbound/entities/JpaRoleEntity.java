package com.topavnbanco.artigos.adapters.outbound.entities;

import com.topavnbanco.artigos.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_role")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JpaRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    private String authority;

    public JpaRoleEntity(Role role) {
        if (role != null) {
            this.id = role.getId();
            this.authority = role.getAuthority();
        }
    }
}
