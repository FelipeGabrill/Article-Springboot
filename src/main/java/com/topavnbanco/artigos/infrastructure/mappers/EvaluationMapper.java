package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaEvaluationEntity;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class, ArticleMapper.class})
public interface EvaluationMapper {

    @Mapping(target = "article.evaluation", ignore = true)
    @Mapping(target = "article.articlesUsers", ignore = true)
    @Mapping(target = "article.reviews", ignore = true)
    @Mapping(target = "article.congresso", ignore = true)
    @Mapping(target = "reviews.article", ignore = true)
    @Mapping(target = "reviews.evaluation", ignore = true)
    @Mapping(target = "reviews.reviewer", ignore = true)
    Evaluation toDomain(JpaEvaluationEntity entity);

    @Mapping(target = "article.evaluation", ignore = true)
    @Mapping(target = "article.articlesUsers", ignore = true)
    @Mapping(target = "article.reviews", ignore = true)
    @Mapping(target = "article.congresso", ignore = true)
    @Mapping(target = "reviews.article", ignore = true)
    @Mapping(target = "reviews.evaluation", ignore = true)
    @Mapping(target = "reviews.reviewer", ignore = true)
    JpaEvaluationEntity toEntity(Evaluation domain);
}