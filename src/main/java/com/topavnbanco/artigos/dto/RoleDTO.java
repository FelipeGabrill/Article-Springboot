package com.topavnbanco.artigos.dto;

import com.topavnbanco.artigos.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoleDTO {

    @Schema(description = "Unique identifier of the role", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Name or authority of the role", example = "ROLE_ADMIN", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authority;

    public RoleDTO(Role role) {
        id = role.getId();
        authority = role.getAuthority();
    }
}