package com.topavnbanco.artigos.infrastructre.specifications;

import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.congresso.enuns.CongressoModality;
import jakarta.persistence.criteria.SetJoin;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.Date;

public class CongressoSpec {

    public static Specification<Congresso> nameContains(String name) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(name)) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Congresso> knowledgeAreaContains(String knowledgeArea) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(knowledgeArea)) return null;
            SetJoin<Congresso, String> join = root.joinSet("knowledgeArea");
            query.distinct(true);
            return cb.like(cb.lower(join), "%" + knowledgeArea.toLowerCase() + "%");
        };
    }

    public static Specification<Congresso> hasCongressoModality(CongressoModality modality) {
        return (root, query, cb) -> {
            if (modality == null) return null;
            return cb.equal(root.get("congressoModality"), modality);
        };
    }

    public static Specification<Congresso> placeContains(String place) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(place)) return null;
            return cb.like(cb.lower(root.get("place")), "%" + place.toLowerCase() + "%");
        };
    }

    public static Specification<Congresso> startsAtOrAfter(Date start) {
        return (root, query, cb) -> start == null ? null : cb.greaterThanOrEqualTo(root.get("startDate"), start);
    }

    public static Specification<Congresso> endsAtOrBefore(Date end) {
        return (root, query, cb) -> end == null ? null : cb.lessThanOrEqualTo(root.get("endDate"), end);
    }
}
