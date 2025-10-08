package com.topavnbanco.artigos.adapters.outbound.entities;

import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.congresso.enuns.CongressoModality;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_congresso")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class JpaCongressoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private byte[] imageThumbnail;

    @Column(unique = true)
    private String name;

    @ElementCollection
    private Set<String> knowledgeArea = new LinkedHashSet<>();

    private String description;

    private String descriptionTitle;

    @Enumerated(EnumType.STRING)
    private CongressoModality congressoModality;

    private Date startDate;

    private Date endDate;

    private String place;

    private Date submissionDeadline;

    private Date reviewDeadline;

    private Integer maxReviewsPerArticle;

    private Integer minReviewsPerArticle;

}
