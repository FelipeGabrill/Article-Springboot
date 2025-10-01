package com.topavnbanco.artigos.jobs;

import com.topavnbanco.artigos.servicies.review.ReviewerAssignmentService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component
public class DistributeMissingReviewsJob implements Job {

    @Autowired
    private ReviewerAssignmentService service;

    @Override
    public void execute(JobExecutionContext context) {
        Long congressoId = context.getJobDetail().getJobDataMap().getLong("congressoId");
        service.assignReviewersForCongress(congressoId);
    }
}

