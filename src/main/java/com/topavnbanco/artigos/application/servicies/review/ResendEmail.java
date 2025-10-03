package com.topavnbanco.artigos.application.servicies.review;

import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.adapters.outbound.repositories.ReviewRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.UserRepository;
import com.topavnbanco.artigos.application.servicies.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResendEmail {

    private static final Logger log = LoggerFactory.getLogger(ResendEmail.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void resendEmail(Long congressoId) {
        Instant nowI = Instant.now();
        Date now = Date.from(nowI);
        Date cutoff = Date.from(nowI.minus(2, ChronoUnit.MINUTES));

        List<Review> pending = reviewRepository.findPendingEligibleByCongresso(congressoId, cutoff, now);
        log.info("Reatribuição: encontradas {} reviews pendentes (cutoff={})", pending.size(), cutoff);

        Map<Long, List<Review>> byArticle = pending.stream()
                .filter(r -> r.getArticle() != null && r.getArticle().getId() != null)
                .filter(r -> r.getArticle().getStatus() == ReviewPerArticleStatus.PENDING)
                .collect(Collectors.groupingBy(r -> r.getArticle().getId()));

        byArticle.forEach(this::processArticle);
    }

    private void processArticle(Long articleId, List<Review> reviewsDoArtigo) {
        Set<Long> excluded = new HashSet<>(reviewRepository.findReviewerIdsByArticle(articleId));

        log.info("Artigo {}: {} reviews pendentes; revisores já usados (antes) = {}",
                articleId, reviewsDoArtigo.size(), excluded);

        for (Review r : reviewsDoArtigo) {
            handleSingleReview(articleId, r, excluded);
        }
    }

    private void handleSingleReview(Long articleId, Review r, Set<Long> excluded) {
        Long reviewId = r.getId();
        Long currentReviewerId = (r.getReviewer() != null ? r.getReviewer().getId() : null);

        userRepository.pickOneRandomEligible(articleId, excluded)
                .ifPresentOrElse(newReviewer -> {
                    r.setReviewer(newReviewer);
                    r.setCreateAt(Date.from(Instant.now()));
                    reviewRepository.save(r);

                    excluded.add(newReviewer.getId());

                    log.info("Reatribuída reviewId={} artigo={} oldReviewer={} newReviewer={}",
                            reviewId, articleId, currentReviewerId, newReviewer.getId());

                    log.info("Send new email to : {}", r.getReviewer().getLogin());
                    // emailService.notifyReviewer(newReviewer, r.getArticle().getTitle());
                }, () -> {
                    if (r.getReviewer() != null) {
                        log.info("Sem novo elegível para reviewId={} artigo={}. Lembrete para reviewerId={}",
                                reviewId, articleId, currentReviewerId);
                        // emailService.notifyPendingReminder(r.getReviewer(), r.getArticle().getTitle());
                    } else {
                        log.warn("Sem novo elegível e review sem reviewer (reviewId={} artigo={})",
                                reviewId, articleId);
                    }
                });
    }
}
