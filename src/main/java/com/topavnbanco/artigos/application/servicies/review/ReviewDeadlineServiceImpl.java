package com.topavnbanco.artigos.application.servicies.review;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaArticleEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaEvaluationEntity;
import com.topavnbanco.artigos.adapters.outbound.entities.JpaReviewEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaArticleRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaCongressoRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaEvaluationRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaReviewRepository;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewDeadlineServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(ReviewDeadlineServiceImpl.class);

    @Autowired
    private JpaReviewRepository reviewRepository;

    @Autowired
    private JpaCongressoRepository congressoRepository;

    @Autowired
    private JpaEvaluationRepository evaluationRepository;

    @Autowired
    private JpaArticleRepository articleRepository;

    @Autowired
    private RankingNotificationServiceImpl rankingNotificationService;

    @Transactional
    public void createEvaluation(Long congressoId) {
        int minReview = congressoRepository.getReferenceById(congressoId).getMinReviewsPerArticle();

        Map<JpaArticleEntity, List<JpaReviewEntity>> grouped = reviewRepository
                .findByArticle_Congresso_Id(congressoId).stream()
                .collect(Collectors.groupingBy(JpaReviewEntity::getArticle));

        grouped.forEach((article, reviews) -> processArticle(article, reviews, minReview));

        rankingNotificationService.notifyTop20(congressoId);
    }

    private void processArticle(JpaArticleEntity article, List<JpaReviewEntity> reviews, int minReview) {
        List<JpaReviewEntity> valid = reviews.stream()
                .filter(r -> r.getScore() != null)
                .filter(r -> r.getComment() != null && !r.getComment().isBlank())
                .toList();

        List<JpaReviewEntity> invalid = reviews.stream()
                .filter(r -> r.getScore() == null
                        || r.getComment() == null
                        || r.getComment().trim().isBlank())
                .toList();

        if (!invalid.isEmpty()) {
            reviewRepository.deleteAll(invalid);
        }

        int qtd = valid.size();
        double media = valid.stream().mapToDouble(JpaReviewEntity::getScore).average().orElse(0.0);
        createOrUpdateEvaluation(article, valid, qtd, media);

        if (qtd >= minReview) {
            article.setStatus(ReviewPerArticleStatus.VALID);
            log.info("Article válido: articleId={} qtdValidas={} minRequired={}", article.getId(), qtd, minReview);
        } else {
            article.setStatus(ReviewPerArticleStatus.EXPIRED);
            log.info("Article expirado: articleId={} qtdValidas={} minRequired={}",
                    article.getId(), qtd, minReview);
        }

        articleRepository.save(article);
        log.info("Média do artigo {} = {}", article.getId(), media);
    }

    private void createOrUpdateEvaluation(JpaArticleEntity article, List<JpaReviewEntity> valid, int qtd, double media) {

        double mediaTruncated = Math.floor(media * 100) / 100.0;

        JpaEvaluationEntity evaluationEntity = evaluationRepository.findByArticle_Id(article.getId())
                .orElseGet(JpaEvaluationEntity::new);
        valid.forEach(r -> r.setEvaluation(evaluationEntity));
        evaluationEntity.setReviews(valid);
        evaluationEntity.setArticle(article);
        evaluationEntity.setNumberOfReviews(qtd);
        evaluationEntity.setFinalScore(mediaTruncated);

        article.setEvaluation(evaluationEntity);
        evaluationRepository.save(evaluationEntity);

        log.info("Evaluation criada/atualizada: articleId={} evalId={} qtd={} media={}",
                article.getId(), evaluationEntity.getId(), qtd, media);
    }
}
