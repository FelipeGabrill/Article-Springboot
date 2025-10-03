package com.topavnbanco.artigos.application.servicies.review;

import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.evaluation.Evaluation;
import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.adapters.outbound.repositories.ArticleRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.CongressoRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.EvaluationRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewDeadlineService {

    private static final Logger log = LoggerFactory.getLogger(ReviewDeadlineService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CongressoRepository congressoRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RankingNotificationService rankingNotificationService;

    @Transactional
    public void createEvaluation(Long congressoId) {
        int minReview = congressoRepository.getReferenceById(congressoId).getMinReviewsPerArticle();

        Map<Article, List<Review>> grouped = reviewRepository
                .findByArticle_Congresso_Id(congressoId).stream()
                .collect(Collectors.groupingBy(Review::getArticle));

        grouped.forEach((article, reviews) -> processArticle(article, reviews, minReview));

        rankingNotificationService.notifyTop20(congressoId);
    }

    private void processArticle(Article article, List<Review> reviews, int minReview) {
        List<Review> valid = reviews.stream()
                .filter(r -> r.getScore() != null)
                .filter(r -> r.getComment() != null && !r.getComment().isBlank())
                .toList();

        List<Review> invalid = reviews.stream()
                .filter(r -> r.getScore() == null
                        || r.getComment() == null
                        || r.getComment().trim().isBlank())
                .toList();

        if (!invalid.isEmpty()) {
            reviewRepository.deleteAll(invalid);
        }

        int qtd = valid.size();
        double media = valid.stream().mapToDouble(Review::getScore).average().orElse(0.0);
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

    private void createOrUpdateEvaluation(Article article, List<Review> valid, int qtd, double media) {

        double mediaTruncated = Math.floor(media * 100) / 100.0;

        Evaluation evaluation = new Evaluation();
        evaluation.setReviews(valid);
        evaluation.setArticle(article);
        evaluation.setNumberOfReviews(qtd);
        evaluation.setFinalScore(mediaTruncated);

        article.setEvaluation(evaluation);
        evaluationRepository.save(evaluation);

        log.info("Evaluation criada/atualizada: articleId={} evalId={} qtd={} media={}",
                article.getId(), evaluation.getId(), qtd, media);
    }
}
