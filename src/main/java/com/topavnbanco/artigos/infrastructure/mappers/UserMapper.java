package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaUserEntity;

import com.topavnbanco.artigos.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {AddressMapper.class, CardMapper.class, RoleMapper.class})
public interface UserMapper {

    @Mapping(target = "reviewer", source = "isReviewer")
    @Mapping(target = "userArticles", ignore = true)
    @Mapping(target = "congresso.user", ignore = true)
    @Mapping(target = "congresso.knowledgeArea", ignore = true)
    User toDomain(JpaUserEntity entity);

    @Mapping(target = "isReviewer", source = "reviewer")
    @Mapping(target = "userArticles", ignore = true)
    @Mapping(target = "congresso.user", ignore = true)
    @Mapping(target = "congresso.knowledgeArea", ignore = true)
    JpaUserEntity toEntity(User domain);

}
