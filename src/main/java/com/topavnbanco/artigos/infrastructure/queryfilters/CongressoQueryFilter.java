package com.topavnbanco.artigos.infrastructure.queryfilters;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCongressoEntity;
import com.topavnbanco.artigos.domain.congresso.enuns.CongressoModality;
import com.topavnbanco.artigos.infrastructure.specifications.CongressoSpec;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

@Data
public class CongressoQueryFilter {

    private String name;
    private String knowledgeArea;
    private CongressoModality congressoModality;
    private Date startDate;
    private Date endDate;
    private String place;

    public Specification<JpaCongressoEntity> toSpecification() {
        return Specification.where(CongressoSpec.nameContains(name))
                .and(CongressoSpec.knowledgeAreaContains(knowledgeArea))
                .and(CongressoSpec.hasCongressoModality(congressoModality))
                .and(CongressoSpec.placeContains(place))
                .and(CongressoSpec.startsAtOrAfter(startDate))
                .and(CongressoSpec.endsAtOrBefore(endDate));
    }
}
