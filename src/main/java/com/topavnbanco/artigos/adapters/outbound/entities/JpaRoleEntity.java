package com.topavnbanco.artigos.adapters.outbound.entities;

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

}
