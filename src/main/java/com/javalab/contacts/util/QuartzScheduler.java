package com.javalab.contacts.util;


import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public final class QuartzScheduler {



    private static final Logger logger = LoggerFactory.getLogger(QuartzScheduler.class);

    private QuartzScheduler(){
    }


    public static void stop(Scheduler scheduler){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error("{}",e.getMessage());
        }
    }

    public static void start(Scheduler scheduler){
        try {
            scheduler.start();
            logger.debug("define the job and tie it to MailerJob.class");
            JobDetail job = newJob(MailerJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInHours(24)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException se) {
            logger.error("{}",se.getMessage());
        }
    }

}
