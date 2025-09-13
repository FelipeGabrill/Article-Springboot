package com.topavnbanco.artigos.job;

import com.topavnbanco.artigos.servicies.ResendEmail;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component
public class DailyToResendNewsJob implements Job {

    @Autowired
    private ResendEmail service;

    @Override
    public void execute(JobExecutionContext context) {
        service.resentEmail();
    }
}

