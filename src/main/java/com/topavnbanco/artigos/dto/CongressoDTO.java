package com.topavnbanco.artigos.dto;

import com.topavnbanco.artigos.dto.user.UserDTO;
import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
public class CongressoDTO {

    @Schema(description = "Identificador único do congresso", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome do congresso", example = "Congresso Internacional de Inteligência Artificial")
    @NotBlank(message = "O nome do congresso é obrigatório.")
    @Size(min = 5, max = 150, message = "O nome deve ter entre 5 e 150 caracteres.")
    private String name;

    @Schema(description = "Imagem do congresso em formato binário (base64)", example = "iVBORw0KGgoAAAANSUhEUgAA")
    private byte[] imageThumbnail;

    @Schema(description = "Data de início do congresso", example = "2025-07-10")
    @NotNull(message = "A data de início é obrigatória.")
    private Date startDate;

    @Schema(description = "Data de término do congresso", example = "2025-07-15")
    @NotNull(message = "A data de término é obrigatória.")
    private Date endDate;

    @Schema(description = "Local onde o congresso será realizado", example = "São Paulo - SP, Brasil")
    @NotBlank(message = "O local do congresso é obrigatório.")
    @Size(min = 3, max = 200, message = "O local deve ter entre 3 e 200 caracteres.")
    private String place;

    @Schema(description = "Número máximo de revisões por artigo", example = "5")
    @Min(value = 3, message = "O número mínimo de revisões deve ser pelo menos 3.")
    @Max(value = 10, message = "O número máximo de revisões não pode ultrapassar 10.")
    @NotNull(message = "O número máximo de revisões por artigo é obrigatório.")
    private Integer maxReviewsPerArticle;

    @Schema(description = "Número mínimo de revisões por artigo", example = "3")
    @Min(value = 3, message = "O número mínimo de revisões deve ser pelo menos 1.")
    @Max(value = 10, message = "O número mínimo de revisões não pode ultrapassar 10.")
    @NotNull(message = "O número mínimo de revisões por artigo é obrigatório.")
    private Integer minReviewsPerArticle;

    @Schema(description = "Data limite para submissão de artigos", example = "2025-06-20")
    @NotNull(message = "A data limite de submissão é obrigatória.")
    private Date submissionDeadline;

    @Schema(description = "Data limite para revisão de artigos", example = "2025-06-20")
    @NotNull(message = "A data limite de revisão é obrigatória.")
    private Date reviewDeadline;

    @Schema(description = "IDs dos usuários associados ao congresso", example = "[12, 34, 56]")
    private List<Long> usersIds= new ArrayList<>();

    public CongressoDTO(Congresso entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.place = entity.getPlace();
        this.submissionDeadline = entity.getSubmissionDeadline();
        this.reviewDeadline = entity.getReviewDeadline();
        this.imageThumbnail = entity.getImageThumbnail();
        this.maxReviewsPerArticle = entity.getMaxReviewsPerArticle();
        this.minReviewsPerArticle = entity.getMinReviewsPerArticle();

        for(User user : entity.getUser()) {
            UserDTO dto = new UserDTO(user);
            usersIds.add(dto.getId());
        }
    }
}
