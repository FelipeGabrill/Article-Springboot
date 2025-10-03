package com.topavnbanco.artigos.domain.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddressDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador único do endereço", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome da rua do endereço", example = "Av. Paulista")
    @NotBlank(message = "A rua não pode estar em branco.")
    @Size(min = 3, max = 100, message = "O nome da rua deve ter entre 3 e 100 caracteres.")
    private String street;

    @Schema(description = "Número do endereço", example = "1000")
    @NotBlank(message = "O número não pode estar em branco.")
    @Pattern(regexp = "^[0-9A-Za-z\\-]+$", message = "O número deve conter apenas dígitos, letras ou hífen (ex: 100, 100A, 100-B).")
    private String number;

    @Schema(description = "Complemento do endereço", example = "Apto 101")
    @Size(max = 50, message = "O complemento deve ter no máximo 50 caracteres.")
    private String complement;

    @Schema(description = "Cidade do endereço", example = "São Paulo")
    @NotBlank(message = "A cidade é obrigatória.")
    @Size(min = 2, max = 60, message = "A cidade deve ter entre 2 e 60 caracteres.")
    private String city;

    @Schema(description = "Estado (UF) do endereço", example = "SP")
    @NotBlank(message = "O estado é obrigatório.")
    @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 letras (ex: SP, RJ).")
    @Pattern(regexp = "^[A-Z]{2}$", message = "O estado deve estar em letras maiúsculas (ex: SP, RJ).")
    private String state;

    @Schema(description = "CEP do endereço", example = "01310-100")
    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$", message = "CEP inválido. Use o formato 00000-000.")
    private String zipCode;

    @Schema(description = "País do endereço", example = "Brasil")
    @NotBlank(message = "O país é obrigatório.")
    @Size(min = 2, max = 60, message = "O país deve ter entre 2 e 60 caracteres.")
    private String country;

    public AddressDTO(Address entity) {
        this.id = entity.getId();
        this.street = entity.getStreet();
        this.number = entity.getNumber();
        this.complement = entity.getComplement();
        this.city = entity.getCity();
        this.state = entity.getState();
        this.zipCode = entity.getZipCode();
        this.country = entity.getCountry();
    }
}
