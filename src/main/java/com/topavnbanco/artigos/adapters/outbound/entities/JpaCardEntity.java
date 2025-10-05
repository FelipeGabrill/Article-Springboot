package com.topavnbanco.artigos.adapters.outbound.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topavnbanco.artigos.domain.card.Card;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "tb_card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JpaCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String number;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
    @DateTimeFormat(pattern = "MM/yyyy")
    private Date expired;

    private Integer cvv;

    public JpaCardEntity(Card card) {
        if (card != null) {
            this.id = card.getId();
            this.number = card.getNumber();
            this.expired = card.getExpired();
            this.cvv = card.getCvv();
        }
    }
}
