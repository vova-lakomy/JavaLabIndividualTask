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
            logger.debug("starting scheduler");
            scheduler.start();
            logger.debug("creating job");
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

            logger.debug("adding job to scheduler");
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException se) {
            logger.error("{}",se.getMessage());
        }
    }

}
