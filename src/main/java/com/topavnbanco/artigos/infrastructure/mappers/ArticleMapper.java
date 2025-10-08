package com.topavnbanco.artigos.infrastructure.mappers;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.domain.article.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ReviewMapper.class, EvaluationMapper.class, CongressoMapper.class})
public interface ArticleMapper {

    @Mapping(target = "reviews.article", ignore = true)
    @Mapping(target = "reviews.evaluation", ignore = true)
    @Mapping(target = "reviews.reviewer.userArticles", ignore = true)
    @Mapping(target = "articlesUsers.userArticles", ignore = true)
    @Mapping(target = "articlesUsers.address", ignore = true)
    @Mapping(target = "articlesUsers.profileImage", ignore = true)
    @Mapping(target = "articlesUsers.card", ignore = true)
    @Mapping(target = "articlesUsers.congresso", ignore = true)
    @Mapping(target = "evaluation.reviews", ignore = true)
    @Mapping(target = "evaluation.article", ignore = true)
    @Mapping(target = "congresso.knowledgeArea", ignore = true)
    Article toDomain(JpaArticleEntity entity);

    @Mapping(target = "reviews.article", ignore = true)
    @Mapping(target = "reviews.evaluation", ignore = true)
    @Mapping(target = "reviews.reviewer.userArticles", ignore = true)
    @Mapping(target = "articlesUsers.userArticles", ignore = true)
    @Mapping(target = "articlesUsers.address", ignore = true)
    @Mapping(target = "articlesUsers.profileImage", ignore = true)
    @Mapping(target = "articlesUsers.card", ignore = true)
    @Mapping(target = "articlesUsers.congresso", ignore = true)
    @Mapping(target = "evaluation.reviews", ignore = true)
    @Mapping(target = "evaluation.article", ignore = true)
    @Mapping(target = "congresso.knowledgeArea", ignore = true)
    JpaArticleEntity toEntity(Article domain);

    List<Article> toDomainList(List<JpaArticleEntity> entities);

    List<JpaArticleEntity> toEntityList(List<Article> domains);

}
