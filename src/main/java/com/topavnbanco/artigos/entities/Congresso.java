package com.topavnbanco.artigos.entities;

import com.topavnbanco.artigos.entities.enuns.CongressoModality;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "tb_congresso")
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Congresso {

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

    @OneToMany(mappedBy = "congresso")
    private List<User> user = new ArrayList<>();

    public Congresso() {
    }

}
