package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.entities.Article;
import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.repositories.ArticleRepository;
import com.topavnbanco.artigos.repositories.CongressoRepository;
import com.topavnbanco.artigos.repositories.ReviewRepository;
import com.topavnbanco.artigos.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ReviewerAssignmentService {

    private static final Logger log = LoggerFactory.getLogger(ReviewerAssignmentService.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CongressoRepository congressoRepository;

    @Transactional
    public void assignReviewersForCongress(Long congressoId) {

        Congresso congresso = congressoRepository.getReferenceById(congressoId);

        List<Article> articles = articleRepository.findByCongressoId(congressoId);
        log.info("Artigos encontrados para congressoId={}: {}", congressoId, articles.size());

        if (articles.isEmpty()) {
            log.warn("Nenhum artigo encontrado para congressoId={}. Nada a atribuir.", congressoId);
            return;
        }

        for (Article a : articles) {
            assignExactlyNReviewers(a, congresso.getMaxReviewsPerArticle());
        }
    }

    private void assignExactlyNReviewers(Article article, int n) {
        List<Long> authorIds = article.getArticlesUsers()
                .stream().map(User::getId).toList();

        List<User> randoms = userRepository.pickRandomEligible(article.getId(), authorIds, n);

        for (User reviewer : randoms) {
            Review r = new Review();
            r.setArticle(article);
            r.setReviewer(reviewer);
            r.setCreateAt(Date.from(Instant.now()));
            reviewRepository.save(r);
            //notifyReviewer(r.getReviewer(), r.getArticle().getTitle());
            log.info("Review criada: reviewId={} articleId={} reviewerId={} login={}",
                    r.getId(), r.getArticle().getId(), r.getReviewer().getId(), r.getReviewer().getLogin());
        }
    }

    public void notifyReviewer(User reviewer, String articleTitle) {
        String subject = "Nova revisão atribuída";
        LocalDate deadline = LocalDate.now().plusDays(5);
        String body = String.format(
                "Olá %s,\n\n" +
                        "Você foi designado para revisar o artigo: %s.\n" +
                        "Por favor, acesse o sistema e realize sua avaliação até o prazo definido: %s.\n\n" +
                        "Atenciosamente,\nEquipe do Congresso",
                reviewer.getUsernameUser(), articleTitle, deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

        emailService.sendEmail(reviewer.getLogin(), subject, body);
    }
}

