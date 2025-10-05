package com.topavnbanco.artigos.application.servicies;

import com.topavnbanco.artigos.application.servicies.exceptions.ResourceNotFoundException;
import com.topavnbanco.artigos.application.usecases.AddressUseCases;
import com.topavnbanco.artigos.domain.address.Address;
import com.topavnbanco.artigos.adapters.inbound.dtos.address.AddressDTO;
import com.topavnbanco.artigos.domain.address.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressUseCases {

    @Autowired
    private AddressRepository repository;

    @Transactional(readOnly = true)
    public AddressDTO findById(Long id) {
        Address address = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new AddressDTO(address);
    }

    @Transactional
    public Address insert(AddressDTO dto) {
        Address entity = new Address();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return entity;
    }

    @Transactional
    public AddressDTO update(Long id, AddressDTO dto) {
        try {
            Address entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new AddressDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    private void copyDtoToEntity(AddressDTO dto, Address entity) {

        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setComplement(dto.getComplement());
        entity.setNumber(dto.getNumber());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setZipCode(dto.getZipCode());
    }
}
