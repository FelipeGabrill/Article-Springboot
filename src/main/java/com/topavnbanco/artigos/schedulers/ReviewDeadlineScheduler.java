package com.topavnbanco.artigos.schedulers;

import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.job.AssignReviewersJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ReviewDeadlineScheduler {

    @Autowired
    private Scheduler scheduler;

    public void scheduleOnReviewDeadline(Congresso c) {
        try {
            JobDetail job = JobBuilder.newJob(AssignReviewersJob.class)
                    .withIdentity("closeOnReview_cong_" + c.getId() + c.getId(), "reviews")
                    .usingJobData("congressoId", c.getId())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .startAt(Date.from(c.getReviewDeadline().toInstant()))
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new IllegalStateException("Falha ao agendar atribuição", e);
        }
    }
}
