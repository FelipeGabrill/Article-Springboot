package com.topavnbanco.artigos.infrastructure.schedulers;

import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.infrastructure.jobs.ResendPendingReviewsJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ReviewDailyCheckScheduler {

    @Autowired
    private Scheduler scheduler;

    public void scheduleOnSubmissionDaily(Congresso c) {
        try {
            JobDetail job = JobBuilder.newJob(ResendPendingReviewsJob.class)
                    .withIdentity("resendNews_cong_" + c.getId(), "reviews")
                    .usingJobData("congressoId", c.getId())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .startAt(Date.from(c.getSubmissionDeadline().toInstant()))
                    .endAt(Date.from(c.getReviewDeadline().toInstant()))
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            //.withIntervalInHours(24)
                            .withIntervalInMinutes(3)
                            .repeatForever()
                            .withMisfireHandlingInstructionFireNow())
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new IllegalStateException("Falha ao agendar atribuição", e);
        }
    }
}
