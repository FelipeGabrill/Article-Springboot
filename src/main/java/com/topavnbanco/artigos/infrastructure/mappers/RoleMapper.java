package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaRoleEntity;
import com.topavnbanco.artigos.domain.role.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toDomain(JpaRoleEntity entity);
    JpaRoleEntity toEntity(Role domain);
}