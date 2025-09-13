package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.repositories.ReviewRepository;
import com.topavnbanco.artigos.repositories.UserRepository;
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
    public void resentEmail() {
        Date cutoff = Date.from(Instant.now().minus(2, ChronoUnit.MINUTES));

        List<Review> pending = reviewRepository.findByScoreIsNullAndCreateAtBefore(cutoff);
        log.info("Reatribuição: encontradas {} reviews pendentes (cutoff={})", pending.size(), cutoff);

        Map<Long, List<Review>> byArticle = pending.stream()
                .filter(r -> r.getArticle() != null && r.getArticle().getId() != null)
                .collect(Collectors.groupingBy(r -> r.getArticle().getId()));

        byArticle.forEach((articleId, reviewsDoArtigo) -> {
            Set<Long> excluded = new HashSet<>(reviewRepository.findReviewerIdsByArticle(articleId));

            log.info("Artigo {}: {} reviews pendentes; revisores já usados (antes) = {}",
                    articleId, reviewsDoArtigo.size(), excluded);

            for (Review r : reviewsDoArtigo) {
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
                            // notifyReviewer(newReviewer, r.getArticle().getTitle());
                        }, () -> {
                            if (r.getReviewer() != null) {
                                log.info("Sem novo elegível para reviewId={} artigo={}. Lembrete para reviewerId={}",
                                        reviewId, articleId, currentReviewerId);
                                // notifyPendingReminder(r.getReviewer(), r.getArticle().getTitle());
                            } else {
                                log.warn("Sem novo elegível e review sem reviewer (reviewId={} artigo={})",
                                        reviewId, articleId);
                            }
                        });
            }
        });
    }

    private void notifyReviewer(User reviewer, String articleTitle) {
        String subject = "Nova revisão atribuída";
        String body = String.format(
                "Olá %s,\n\n" +
                        "Você foi designado para revisar o artigo: %s.\n" +
                        "Por favor, acesse o sistema e realize sua avaliação no prazo estabelecido.\n\n" +
                        "Atenciosamente,\nEquipe do Congresso",
                reviewer.getUsernameUser(), articleTitle
        );

        emailService.sendEmail(reviewer.getLogin(), subject, body);
    }

    private void notifyPendingReminder(User reviewer, String articleTitle) {
        String subject = "Lembrete: revisão pendente";
        String body = String.format(
                "Olá %s,\n\n" +
                        "Você ainda possui uma revisão pendente para o artigo: %s.\n" +
                        "Gentileza concluir a avaliação o quanto antes.\n\n" +
                        "Atenciosamente,\nEquipe do Congresso",
                reviewer.getUsernameUser(), articleTitle
        );

        emailService.sendEmail(reviewer.getLogin(), subject, body);
    }

}
