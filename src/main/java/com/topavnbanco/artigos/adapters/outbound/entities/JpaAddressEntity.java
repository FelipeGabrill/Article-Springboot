package com.topavnbanco.artigos.adapters.outbound.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JpaAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String street;

    private String number;

    private String complement;

    private String city;

    private String state;

    private String zipCode;

    private String country;
}
