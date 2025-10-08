package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCongressoEntity;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CongressoMapper {

    Congresso toDomain(JpaCongressoEntity entity);

    JpaCongressoEntity toEntity(Congresso domain);
}