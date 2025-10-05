package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaAddressEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaAddressRepository;
import com.topavnbanco.artigos.domain.address.Address;
import com.topavnbanco.artigos.domain.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    @Autowired
    private JpaAddressRepository jpaAddressRepository;

    @Override
    public Address save(Address address) {
        JpaAddressEntity a = jpaAddressRepository.save(new JpaAddressEntity(address));
        return new Address(
                a.getId(),
                a.getStreet(),
                a.getNumber(),
                a.getComplement(),
                a.getCity(),
                a.getState(),
                a.getZipCode(),
                a.getCountry()
        );
    }

    @Override
    public Optional<Address> findById(Long id) {
        return jpaAddressRepository.findById(id)
                .map(a -> new Address(
                        a.getId(),
                        a.getStreet(),
                        a.getNumber(),
                        a.getComplement(),
                        a.getCity(),
                        a.getState(),
                        a.getZipCode(),
                        a.getCountry()
                ));
    }

    @Override
    public Address getReferenceById(Long id) {
        JpaAddressEntity a = jpaAddressRepository.getReferenceById(id);

        return new Address(
                a.getId(),
                a.getStreet(),
                a.getNumber(),
                a.getComplement(),
                a.getCity(),
                a.getState(),
                a.getZipCode(),
                a.getCountry()
        );
    }

    @Override
    public void deleteById(Long id) {
        jpaAddressRepository.deleteById(id);
    }
}
