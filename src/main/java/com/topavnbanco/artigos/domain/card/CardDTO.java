package com.topavnbanco.artigos.domain.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class CardDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Identificador único do cartão", example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Número do cartão de crédito (16 dígitos)", example = "1234567812345678")
    @NotBlank(message = "O número do cartão é obrigatório.")
    @Pattern(regexp = "^[0-9]{16}$", message = "O número do cartão deve conter exatamente 16 dígitos numéricos.")
    private String number;

    @Schema(description = "Data de expiração do cartão", example = "2027-12-01")
    @NotNull(message = "A data de expiração é obrigatória.")
    @Future(message = "A data de expiração deve estar no futuro.")
    private Date expired;

    @Schema(description = "Código de segurança do cartão (CVV, 3 dígitos)", example = "123")
    @NotNull(message = "O CVV é obrigatório.")
    @Min(value = 100, message = "O CVV deve ter no mínimo 3 dígitos.")
    @Max(value = 999, message = "O CVV deve ter no máximo 3 dígitos.")
    private Integer cvv;

    public CardDTO(Card entity) {
        this.id = entity.getId();
        this.number = entity.getNumber();
        this.expired = entity.getExpired();
        this.cvv = entity.getCvv();
    }
}
