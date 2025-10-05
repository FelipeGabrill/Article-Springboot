package com.topavnbanco.artigos.infrastructure.jobs;

import com.topavnbanco.artigos.application.servicies.review.ReviewerAssignmentServiceImpl;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component
public class DistributeMissingReviewsJob implements Job {

    @Autowired
    private ReviewerAssignmentServiceImpl service;

    @Override
    public void execute(JobExecutionContext context) {
        Long congressoId = context.getJobDetail().getJobDataMap().getLong("congressoId");
        service.assignReviewersForCongress(congressoId);
    }
}

