package com.topavnbanco.artigos.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topavnbanco.artigos.dto.AddressDTO;
import com.topavnbanco.artigos.dto.CardDTO;
import com.topavnbanco.artigos.dto.RoleDTO;
import com.topavnbanco.artigos.entities.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class UserDTO {

    @Schema(description = "Identificador único do usuário", example = "100", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(description = "Nome de exibição do usuário", example = "test test")
    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String usernameUser;

    @Schema(description = "Login único para autenticação", example = "test@gmail.com")
    @NotBlank(message = "O login é obrigatório.")
    @Email(message = "O login deve ser um e-mail válido.")
    @Size(max = 255, message = "O login deve ter no máximo 255 caracteres.")
    private String login;

    @NotBlank(message = "O local de trabalho é obrigatório.")
    @Schema(description = "Instituição ou empresa de vínculo do usuário", example = "Universidade de São Paulo")
    @Size(max = 150, message = "O local de trabalho pode ter no máximo 150 caracteres.")
    private String workPlace;

    @Schema(description = "Número único de associação gerado automaticamente", example = "550e8400-e29b-41d4-a716-446655440000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID membershipNumber;

    @Schema(description = "Indica se o usuário pode atuar como revisor", example = "true")
    @NotNull(message = "O campo isReviewer é obrigatório.")
    private Boolean isReviewer;

    @Valid
    @NotNull(message = "O endereço é obrigatório na criação.")
    @Schema(description = "Identificador do endereço associado ao usuário", example = "1")
    private AddressDTO addressDTO;

    @Valid
    @Schema(description = "Identificador do cartão associado ao usuário", example = "1")
    @NotNull(message = "O cartão é obrigatório na criação.")
    private CardDTO cardDTO;

    @Schema(description = "Imagem de perfil do usuário em formato binário (base64)", example = "iVBORw0KGgoAAAANSUhEUgAA")
    private byte[] profileImage;

    @Schema(description = "Identificador do congresso associado ao usuário", example = "1")
    @NotNull(message = "O congresso é obrigatório na criação.")
    @Positive(message = "O congressoId deve ser maior que zero.")
    private Long congressoId;

    @Schema(description = "Perfis (roles) atribuídos ao usuário")
    @NotEmpty(message = "Informe ao menos uma role na criação.")
    @UniqueElements(message = "Não repita as mesmas roles.")
    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.usernameUser = entity.getUsernameUser();
        this.login = entity.getUsername();
        this.workPlace = entity.getWorkPlace();
        this.membershipNumber = entity.getMembershipNumber();
        this.isReviewer = entity.getIsReviewer();
        this.addressDTO = new AddressDTO(entity.getAddress());
        this.cardDTO = new CardDTO(entity.getCard());
        this.congressoId = entity.getCongresso().getId();
        this.profileImage = entity.getProfileImage();

        if (entity.getRoles() != null) {
            entity.getRoles().forEach(r -> this.roles.add(new RoleDTO(r)));
        }
    }
}