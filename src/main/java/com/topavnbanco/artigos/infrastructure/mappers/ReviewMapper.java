package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaReviewEntity;
import com.topavnbanco.artigos.domain.review.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EvaluationMapper.class, ArticleMapper.class})
public interface ReviewMapper {
    @Mapping(target = "article.reviews", ignore = true)
    @Mapping(target = "article.articlesUsers", ignore = true)
    @Mapping(target = "article.evaluation", ignore = true)
    @Mapping(target = "article.congresso.user", ignore = true)
    @Mapping(target = "article.knowledgeArea", ignore = true)
    @Mapping(target = "evaluation.article", ignore = true)
    @Mapping(target = "evaluation.reviews", ignore = true)
    @Mapping(target = "reviewer.userArticles", ignore = true)
    @Mapping(target = "reviewer.congresso", ignore = true)
    Review toDomain(JpaReviewEntity entity);

    @Mapping(target = "article.reviews", ignore = true)
    @Mapping(target = "article.articlesUsers", ignore = true)
    @Mapping(target = "article.evaluation", ignore = true)
    @Mapping(target = "article.congresso.user", ignore = true)
    @Mapping(target = "article.knowledgeArea", ignore = true)
    @Mapping(target = "evaluation.article", ignore = true)
    @Mapping(target = "evaluation.reviews", ignore = true)
    @Mapping(target = "reviewer.userArticles", ignore = true)
    @Mapping(target = "reviewer.congresso", ignore = true)
    JpaReviewEntity toEntity(Review domain);
}