package com.topavnbanco.artigos.dto.user;

import com.topavnbanco.artigos.dto.ArticleDTO;
import com.topavnbanco.artigos.dto.RoleDTO;
import com.topavnbanco.artigos.entities.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class UserDTO {

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

    @Schema(description = "Senha de acesso do usuário (mínimo 8 caracteres, contendo letra maiúscula, minúscula, número e caractere especial)", example = "StrongP@ss123")
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, max = 60, message = "A senha deve ter entre 8 e 60 caracteres.")
    private String password;

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

    @Schema(description = "Imagem de perfil do usuário em formato binário (base64)", example = "iVBORw0KGgoAAAANSUhEUgAA...")
    private byte[] profileImage;

    @Schema(description = "Identificador do congresso associado ao usuário", example = "1")
    private Long congressoId;

    @Schema(description = "Perfis (roles) atribuídos ao usuário")
    private Set<RoleDTO> rolesDTO = new HashSet<>();

    @Schema(description = "Artigos vinculados ao usuário")
    private Set<Long> userArticlesDTO = new HashSet<>();

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.usernameUser = entity.getUsernameUser();
        this.login = entity.getUsername();
        this.password = entity.getPassword();
        this.workPlace = entity.getWorkPlace();
        this.membershipNumber = entity.getMembershipNumber();
        this.isReviewer = entity.getIsReviewer();
        this.addressId = entity.getAddress().getId();
        this.cardId = entity.getCard().getId();
        this.congressoId = entity.getCongresso().getId();
        this.profileImage = entity.getProfileImage();

        if (entity.getRoles() != null) {
            entity.getRoles().forEach(r -> this.rolesDTO.add(new RoleDTO(r)));
        }
        if (entity.getUserArticles() != null) {
            for (Article a : entity.getUserArticles()) {
                this.userArticlesDTO.add(a.getId());
            }
        }
    }
}