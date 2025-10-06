package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCongressoEntity;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CongressoMapper {

    @Mapping(target = "user.userArticles", ignore = true)
    @Mapping(target = "user.congresso", ignore = true)
    Congresso toDomain(JpaCongressoEntity entity);

    @Mapping(target = "user.userArticles", ignore = true)
    @Mapping(target = "user.congresso", ignore = true)
    JpaCongressoEntity toEntity(Congresso domain);
}