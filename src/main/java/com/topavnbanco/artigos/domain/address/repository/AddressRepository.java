package com.topavnbanco.artigos.domain.address.repository;

import com.topavnbanco.artigos.domain.address.Address;

import java.util.Optional;

public interface AddressRepository {

    Address save(Address address);

    Optional<Address> findById(Long id);

    Address getReferenceById(Long id);

    void deleteById(Long id);
}
