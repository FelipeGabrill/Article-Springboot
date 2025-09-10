package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.entities.Article;
import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.repositories.ArticleRepository;
import com.topavnbanco.artigos.repositories.ReviewRepository;
import com.topavnbanco.artigos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewerAssignmentService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EmailService emailService;

    @Value("${reviews.per.article:5}")
    private int reviewersPerArticle;

    @Value("${reviews.min.required:3}")
    private int minRequiredReviews;

    @Transactional
    public void assignForCongress(Long congressoId) {
        List<Article> articles = articleRepository.findByCongressoId(congressoId);
        System.out.println("test");

        if (articles.isEmpty()) {
            return;
        }

        for (Article a : articles) {
            assignExactlyN(a, reviewersPerArticle);
        }
    }

    private void assignExactlyN(Article article, int n) {
        List<Long> authorIds = article.getArticlesUsers()
                .stream().map(User::getId).toList();

        List<User> randoms = userRepository.pickRandomEligible(article.getId(), authorIds, n);

        for (User reviewer : randoms) {
            Review r = new Review();
            r.setArticle(article);
            r.setReviewer(reviewer);
            reviewRepository.save(r);
            notifyReviewer(reviewer, article.getDescription());
            System.out.println(r.getReviewer().getLogin());
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

