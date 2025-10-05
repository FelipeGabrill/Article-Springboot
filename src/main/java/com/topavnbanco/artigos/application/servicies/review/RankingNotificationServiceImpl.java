package com.topavnbanco.artigos.application.servicies.review;

import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.repository.ArticleRepository;
import com.topavnbanco.artigos.domain.congresso.repository.CongressoRepository;
import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaArticleRepository;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaCongressoRepository;
import com.topavnbanco.artigos.application.servicies.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RankingNotificationServiceImpl {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CongressoRepository congressoRepository;

    @Autowired
    private EmailServiceImpl emailService;

    private static final Logger log = LoggerFactory.getLogger(RankingNotificationServiceImpl.class);


    public void notifyTop20(Long congressoId) {
        List<Article> top20 = fetchTop20Valid(congressoId);
        if (top20.isEmpty()) {
            log.info("Nenhum artigo VALID para notificar no congresso {}", congressoId);
            return;
        }

        String subject = buildRankingSubject(congressoId);
        String body    = buildRankingEmailBody(top20);
        Set<String> recipients = buildRecipients(congressoId, top20);

        log.info(">>> envio de e-mails do TOP 20 <<<");
        log.info("Assunto: {}", subject);
        log.info("Corpo:\n{}", body);

        recipients.forEach(to ->
                log.info("Simulando envio de e-mail para {} (congressoId={})", to, congressoId)
        );
        //emailService.sendEmailBulk(recipients, subject, body);
        log.info("Ranking TOP 20 enviado para {} destinatários (congressoId={})", recipients.size(), congressoId);
    }

    private List<Article> fetchTop20Valid(Long congressoId) {
        return articleRepository
                .findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(
                        congressoId, ReviewPerArticleStatus.VALID, PageRequest.of(0, 20))
                .getContent();
    }

    private String buildRankingSubject(Long congressoId) {
        return "TOP 20 Artigos – Maiores médias do congresso #" + congressoId;
    }

    private Set<String> buildRecipients(Long congressoId, List<Article> top20) {
        var congresso = congressoRepository.getReferenceById(congressoId);

        Set<String> recipients = congresso.getUser().stream()
                .map(User::getLogin)
                .filter(e -> e != null && !e.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        top20.forEach(a -> a.getArticlesUsers().forEach(u -> {
            String email = u.getLogin();
            if (email != null && !email.isBlank()) recipients.add(email);
        }));

        return recipients;
    }

    private String buildRankingEmailBody(List<Article> top20) {
        StringBuilder sb = new StringBuilder();
        sb.append("Segue a lista final com os 20 artigos com maiores médias (status VALID):\n\n");
        for (int i = 0; i < top20.size(); i++) {
            Article a = top20.get(i);
            double score = a.getEvaluation() != null ? a.getEvaluation().getFinalScore() : 0.0;
            double scoreTrunc = Math.floor(score * 100) / 100.0; // 2 casas, sem arredondar
            String title = a.getTitle() != null ? a.getTitle() : "(sem título)";
            sb.append(String.format("%02d) #%d - %s  (média: %.2f)%n",
                    (i + 1), a.getId(), title, scoreTrunc));
        }
        return sb.toString();
    }
}
