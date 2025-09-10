package com.topavnbanco.artigos.job;

import com.topavnbanco.artigos.servicies.ReviewerAssignmentService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component
public class AssignReviewersJob implements Job {

    @Autowired
    private ReviewerAssignmentService service;

    @Override
    public void execute(JobExecutionContext context) {
        Long congressoId = context.getJobDetail().getJobDataMap().getLong("congressoId");
        service.assignForCongress(congressoId);
    }
}

