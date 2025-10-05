package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.domain.address.Address;
import com.topavnbanco.artigos.adapters.inbound.dtos.address.AddressDTO;

public interface AddressUseCases {

    AddressDTO findById(Long id);

    Address insert(AddressDTO dto);

    AddressDTO update(Long id, AddressDTO dto);
}
