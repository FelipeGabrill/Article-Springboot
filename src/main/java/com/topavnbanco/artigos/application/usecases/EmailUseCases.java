package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.domain.user.User;

import java.util.Collection;

public interface EmailUseCases {

    void sendEmail(String to, String subject, String body);

    void sendEmailBulk(Collection<String> recipients, String subject, String body);

    void notifyPendingReminder(User reviewer, String articleTitle);

    void notifyReviewer(User reviewer, String articleTitle);

}
