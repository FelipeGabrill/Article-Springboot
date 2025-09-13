package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.entities.Evaluation;
import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.entities.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.repositories.ArticleRepository;
import com.topavnbanco.artigos.repositories.CongressoRepository;
import com.topavnbanco.artigos.repositories.EvaluationRepository;
import com.topavnbanco.artigos.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Transactional
    public void createEvaluation(Long congressoId) {

        final int minReview = congressoRepository.getReferenceById(congressoId).getMinReviewsPerArticle();


        reviewRepository.findByArticle_Congresso_Id(congressoId).stream()
                .collect(Collectors.groupingBy(Review::getArticle))
                .forEach((article, reviews) -> {
                    List<Review> validas = reviews.stream()
                            .filter(r -> r.getComment() != null && !r.getComment().isBlank())
                            .filter(r -> r.getScore() != null)
                            .toList();

                    int qtd = validas.size();
                    double media = validas.stream()
                            .mapToDouble(Review::getScore)
                            .average()
                            .orElse(0.0);

                    if (qtd >= minReview) {
                        Evaluation evaluation = new Evaluation();
                        evaluation.setReviews(validas);
                        evaluation.setArticle(article);
                        evaluation.setNumberOfReviews(qtd);
                        evaluation.setFinalScore(media);
                        evaluationRepository.save(evaluation);

                        article.setStatus(ReviewPerArticleStatus.VALID);
                        log.info("Evaluation criada/atualizada: articleId={} evalId={} qtd={} media={}",
                                article.getId(), evaluation.getId(), qtd, media);
                    } else {
                        article.setStatus(ReviewPerArticleStatus.EXPIRED);
                        log.info("Article expirado: articleId={} qtdValidas={} minRequired={}",
                                article.getId(), qtd, minReview);
                    }

                    log.info("Media do artigo " + article.getId() + "= " + article.getEvaluation().getId());

                    articleRepository.save(article);
                });
    }

}
