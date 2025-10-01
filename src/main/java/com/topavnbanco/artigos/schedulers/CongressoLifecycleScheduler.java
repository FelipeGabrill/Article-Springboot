package com.topavnbanco.artigos.schedulers;

import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.jobs.DistributeMissingReviewsJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CongressoLifecycleScheduler {

    @Autowired
    private Scheduler scheduler;

    public void scheduleOnSubmissionDeadline(Congresso c) {
        try {
            JobDetail job = JobBuilder.newJob(DistributeMissingReviewsJob.class)
                    .withIdentity("assignOnSubmission_cong_" + c.getId(), "reviews")
                    .usingJobData("congressoId", c.getId())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .startAt(Date.from(c.getSubmissionDeadline().toInstant()))
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new IllegalStateException("Falha ao agendar atribuição", e);
        }
    }
}
