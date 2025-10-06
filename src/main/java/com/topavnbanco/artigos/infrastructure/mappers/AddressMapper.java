package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaAddressEntity;
import com.topavnbanco.artigos.domain.address.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toDomain(JpaAddressEntity entity);

    JpaAddressEntity toEntity(Address domain);
}