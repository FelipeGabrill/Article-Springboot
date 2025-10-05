package com.topavnbanco.artigos.adapters.inbound.dtos.user;

import com.topavnbanco.artigos.adapters.inbound.dtos.role.RoleDTO;
import com.topavnbanco.artigos.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class UserSimpleDTO {
    @Schema(description = "Identificador único do usuário", example = "100", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome de exibição do usuário", example = "Alex pedro")
    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String usernameUser;

    @Schema(description = "Login único para autenticação", example = "Alexpedro@gmail.com")
    @NotBlank(message = "O login é obrigatório.")
    @Email(message = "O login deve ser um e-mail válido.")
    private String login;

    @Schema(description = "Instituição ou empresa de vínculo do usuário", example = "Universidade de São Paulo")
    @Size(max = 150, message = "O local de trabalho pode ter no máximo 150 caracteres.")
    private String workPlace;

    @Schema(description = "Número único de associação gerado automaticamente", example = "550e8400-e29b-41d4-a716-446655440000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID membershipNumber;

    @Schema(description = "Indica se o usuário pode atuar como revisor", example = "true")
    @NotNull(message = "O campo isReviewer é obrigatório.")
    private Boolean isReviewer;

    @Schema(description = "Identificador do endereço associado ao usuário", example = "1")
    private Long addressId;

    @Schema(description = "Identificador do cartão associado ao usuário", example = "1")
    private Long cardId;

    @Schema(description = "Imagem de perfil do usuário em formato binário (base64)", example = "iVBORw0KGgoAAAANSUhEUgAA")
    @NotBlank(message = "A imagem de perfil é obrigatória.")
    private byte[] profileImage;

    @Schema(description = "Identificador do congresso associado ao usuário", example = "1")
    @NotNull(message = "O congresso é obrigatório.")
    private Long congressoId;

    @NotEmpty(message = "Informe ao menos uma role.")
    @Schema(description = "Perfis (roles) atribuídos ao usuário")
    private Set<RoleDTO> roles = new HashSet<>();

    public UserSimpleDTO(User entity) {
        this.id = entity.getId();
        this.usernameUser = entity.getUsernameUser();
        this.login = entity.getUsername();
        this.workPlace = entity.getWorkPlace();
        this.membershipNumber = entity.getMembershipNumber();
        this.isReviewer = entity.getReviewer();
        this.addressId = entity.getAddress().getId();
        this.cardId = entity.getCard().getId();
        this.congressoId = entity.getCongresso().getId();
        this.profileImage = entity.getProfileImage();

        if (entity.getRoles() != null) {
            entity.getRoles().forEach(r -> this.roles.add(new RoleDTO(r)));
        }
    }
}
