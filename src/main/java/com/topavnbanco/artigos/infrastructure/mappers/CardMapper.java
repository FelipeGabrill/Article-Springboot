package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCardEntity;
import com.topavnbanco.artigos.domain.card.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    Card toDomain(JpaCardEntity entity);

    JpaCardEntity toEntity(Card domain);
}