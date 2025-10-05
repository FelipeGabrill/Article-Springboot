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

    @OneToMany(mappedBy = "congresso")
    private List<JpaUserEntity> user = new ArrayList<>();

    public JpaCongressoEntity(Congresso congresso) {
        if (congresso != null) {
            this.id = congresso.getId();
            this.imageThumbnail = congresso.getImageThumbnail();
            this.name = congresso.getName();
            this.knowledgeArea = congresso.getKnowledgeArea() != null
                    ? new LinkedHashSet<>(congresso.getKnowledgeArea())
                    : new LinkedHashSet<>();
            this.description = congresso.getDescription();
            this.descriptionTitle = congresso.getDescriptionTitle();
            this.congressoModality = congresso.getCongressoModality();
            this.startDate = congresso.getStartDate();
            this.endDate = congresso.getEndDate();
            this.place = congresso.getPlace();
            this.submissionDeadline = congresso.getSubmissionDeadline();
            this.reviewDeadline = congresso.getReviewDeadline();
            this.maxReviewsPerArticle = congresso.getMaxReviewsPerArticle();
            this.minReviewsPerArticle = congresso.getMinReviewsPerArticle();
            if (congresso.getUser() == null || congresso.getUser().isEmpty()) {
                this.user = new ArrayList<>();
            } else {
                this.user = congresso.getUser().stream()
                        .filter(u -> u != null && u.getId() != null)
                        .map(u -> {
                            JpaUserEntity e = new JpaUserEntity();
                            e.setId(u.getId());
                            return e;
                        })
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        }


    }
}
