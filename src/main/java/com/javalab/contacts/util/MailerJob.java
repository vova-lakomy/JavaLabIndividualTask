package com.javalab.contacts.util;


import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
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
    private ContactDtoRepository repository = new ContactDtoRepositoryImpl();
    private MailSender mailSender = new MailSender();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("executing mailer job, starting birthday people search ... ");
        String messageText = createBirthdayPeopleMessageForAdministrator();
        if (isNotBlank(messageText)){
            logger.debug("sending email to administrator");
            String mailSubject = "Birthdays for today";
            mailSender.sendMailToAdministrator(mailSubject, messageText);
        } else {
            logger.debug("not found birthday people for today");
        }
    }

    private String createBirthdayPeopleMessageForAdministrator() {
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
                Integer yearOfBirth = Integer.valueOf(dateOfBirth.substring(dateOfBirth.lastIndexOf(".") + 1));
                Integer presentYear = LocalDate.now().getYear();
                Integer age = presentYear - yearOfBirth;
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

    private Collection<ContactShortDTO> findBirthdayPeople(LocalDate todayDate) {
        Integer day = todayDate.getDayOfMonth();
        Integer month = todayDate.getMonthValue();
        return repository.getByDayAndMonth(day, month);
    }
}
