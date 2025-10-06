package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaAddressEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaAddressRepository;
import com.topavnbanco.artigos.domain.address.Address;
import com.topavnbanco.artigos.domain.address.repository.AddressRepository;
import com.topavnbanco.artigos.infrastructure.mappers.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    @Autowired
    private JpaAddressRepository jpaAddressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Address save(Address address) {
        JpaAddressEntity entity = addressMapper.toEntity(address);
        entity = jpaAddressRepository.save(entity);
        return addressMapper.toDomain(entity);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return jpaAddressRepository.findById(id)
                .map(addressMapper::toDomain);
    }

    @Override
    public Address getReferenceById(Long id) {
        JpaAddressEntity entity = jpaAddressRepository.getReferenceById(id);
        return addressMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        jpaAddressRepository.deleteById(id);
    }
}
