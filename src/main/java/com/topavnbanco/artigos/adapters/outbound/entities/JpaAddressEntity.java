package com.topavnbanco.artigos.adapters.outbound.entities;

import com.topavnbanco.artigos.domain.address.Address;
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

    public JpaAddressEntity(Address address) {
        if (address != null) {
            this.id = address.getId();
            this.street = address.getStreet();
            this.number = address.getNumber();
            this.complement = address.getComplement();
            this.city = address.getCity();
            this.state = address.getState();
            this.zipCode = address.getZipCode();
            this.country = address.getCountry();
        }
    }
}
