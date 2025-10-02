package com.topavnbanco.artigos.queryfilters;

import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.entities.enuns.CongressoModality;
import com.topavnbanco.artigos.specifications.CongressoSpec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class CongressoQueryFilter {

    private String name;
    private String knowledgeArea;
    private CongressoModality congressoModality;
    private Date startDate;
    private Date endDate;
    private String place;

    public Specification<Congresso> toSpecification() {
        return Specification.where(CongressoSpec.nameContains(name))
                .and(CongressoSpec.knowledgeAreaContains(knowledgeArea))
                .and(CongressoSpec.hasCongressoModality(congressoModality))
                .and(CongressoSpec.placeContains(place))
                .and(CongressoSpec.startsAtOrAfter(startDate))
                .and(CongressoSpec.endsAtOrBefore(endDate));
    }
}
