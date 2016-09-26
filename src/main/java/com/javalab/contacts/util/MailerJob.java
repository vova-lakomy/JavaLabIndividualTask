package com.javalab.contacts.util;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailerJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(MailerJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("Quartz sending mails ....");
    }
}
