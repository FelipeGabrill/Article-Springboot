package com.topavnbanco.artigos.application.servicies;

import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.application.servicies.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String body) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            emailSender.send(message);
        }
        catch (MailException e){
            throw new EmailException("Failed to send email");
        }
    }

    public void sendEmailBulk(Collection<String> recipients, String subject, String body) {
        for (String to : recipients) {
            if (to == null || to.isBlank()){
                continue;
            }
            sendEmail(to, subject, body);
        }
    }

    public void notifyPendingReminder(User reviewer, String articleTitle) {
        String subject = "Lembrete: revisão pendente";
        String body = String.format(
                "Olá %s,\n\n" +
                        "Você ainda possui uma revisão pendente para o artigo: %s.\n" +
                        "Gentileza concluir a avaliação o quanto antes.\n\n" +
                        "Atenciosamente,\nEquipe do Congresso",
                reviewer.getUsernameUser(), articleTitle
        );

        sendEmail(reviewer.getLogin(), subject, body);
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

       sendEmail(reviewer.getLogin(), subject, body);
    }
}