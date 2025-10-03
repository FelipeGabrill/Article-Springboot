package com.topavnbanco.artigos.infrastructre.schedulers;

import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.infrastructre.jobs.AverageCalculationJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CongressoReviewDeadlineScheduler {

    @Autowired
    private Scheduler scheduler;

    public void scheduleOnReviewDeadline(Congresso c) {
        try {
            JobDetail job = JobBuilder.newJob(AverageCalculationJob.class)
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
