package com.topavnbanco.artigos.domain.address;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_address")
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {

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

    public Address() {
    }
}
