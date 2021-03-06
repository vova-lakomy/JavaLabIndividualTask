package com.javalab.contacts.util;


import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.exception.SendingMailException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class MailerJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(MailerJob.class);
    private ContactRepository repository = new ContactRepositoryImpl();
    private MailSender mailSender = new MailSender();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("executing mailer job, starting birthday people search ... ");
        String messageText = null;
        try {
            messageText = createBirthdayPeopleMessageForAdministrator();
        } catch (ConnectionFailedException e) {
            logger.error("could not connect to data base");
            throw  new JobExecutionException("could not connect to db");
        }
        if (isNotBlank(messageText)){
            logger.debug("sending email to administrator");
            String mailSubject = "Birthdays for today";
            try {
                mailSender.sendMailToAdministrator(mailSubject, messageText);
            } catch (SendingMailException e) {
                throw new JobExecutionException(e);
            }
        } else {
            logger.debug("not found birthday people for today");
        }
    }

    private String createBirthdayPeopleMessageForAdministrator() throws ConnectionFailedException {
        logger.debug("creating message for administrator");
        String message = "";
        LocalDate todayDate = LocalDate.now();
        Collection<ContactShortDTO> birthdayPeople = findBirthdayPeople(todayDate);
        if (birthdayPeople.size() > 0){
            StringBuilder messageBody = new StringBuilder();
            messageBody.append("<html><head></head><body>");
            messageBody.append("<h3>Birthday people for today</h3>");
            messageBody.append("<table cellpadding=\"5px\">");
            messageBody.append("<tr><th>Name</th><th>Age</th><th>E-mail</th></tr>");
            birthdayPeople.forEach(person -> {
                String fullName = person.getFullName();
                String email = person.geteMail();
                String dateOfBirth = person.getDateOfBirth();
                Integer age = 0;
                if (dateOfBirth != null) {
                    Integer yearOfBirth = Integer.valueOf(dateOfBirth.substring(dateOfBirth.lastIndexOf(".") + 1));
                    Integer presentYear = LocalDate.now().getYear();
                    age = presentYear - yearOfBirth;
                }
                messageBody.append("<tr><td>");
                messageBody.append(fullName);
                messageBody.append("</td><td>");
                messageBody.append(age);
                messageBody.append("</td><td><a href=\"mailto:");
                messageBody.append(email);
                messageBody.append("\">");
                messageBody.append(email);
                messageBody.append("</a></td></tr>");
            });
            messageBody.append("</table></body></html>");
            message = messageBody.toString();
        }
        return message;
    }

    private Collection<ContactShortDTO> findBirthdayPeople(LocalDate todayDate) throws ConnectionFailedException {
        Integer day = 1;
        Integer month = 1;
        if (todayDate != null) {
            day = todayDate.getDayOfMonth();
            month = todayDate.getMonthValue();
        }
        return repository.getByDayAndMonth(day, month);
    }
}
