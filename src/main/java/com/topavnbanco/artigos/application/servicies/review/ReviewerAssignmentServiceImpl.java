package com.topavnbanco.artigos.application.servicies.review;

import com.topavnbanco.artigos.application.servicies.EmailServiceImpl;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.repository.ArticleRepository;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.congresso.repository.CongressoRepository;
import com.topavnbanco.artigos.domain.review.Review;
import com.topavnbanco.artigos.domain.review.repository.ReviewRepository;
import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ReviewerAssignmentServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(ReviewerAssignmentServiceImpl.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EmailServiceImpl emailService;

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
            //emailService.notifyReviewer(r.getReviewer(), r.getArticle().getTitle());
            log.info("Review criada: reviewId={} articleId={} reviewerId={} login={}",
                    r.getId(), r.getArticle().getId(), r.getReviewer().getId(), r.getReviewer().getLogin());
        }
    }
}

