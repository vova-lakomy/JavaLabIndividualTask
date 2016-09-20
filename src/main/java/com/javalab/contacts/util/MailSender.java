package com.javalab.contacts.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    private static final Logger logger = LogManager.getLogger(MailSender.class);

    private Properties mailProps = PropertiesProvider.getInstance().getMailProperties();

    public MailSender() {
    }

    public void sendMail(Address[] addresses, String mailSubject, String messageText) {

        try {
            Message message = new MimeMessage(getMailSession(getMailServerProperties()));
            message.setFrom(new InternetAddress(mailProps.getProperty("mail.from")));
            message.setReplyTo(InternetAddress.parse(mailProps.getProperty("mail.from")));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(mailSubject);
            message.setContent(messageText,"text/html;charset=utf-8");
            Transport.send(message);
            logger.info("sending e-mail to" + addresses + " done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private Session getMailSession(Properties serverProperties) {
        return Session.getDefaultInstance(serverProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailProps.getProperty("mail.login"),
                        mailProps.getProperty("mail.password"));
            }
        });
    }

    private Properties getMailServerProperties(){
        Properties serverProperties = new Properties();
        serverProperties.put("mail.smtp.host", mailProps.getProperty("mail.smtp.host"));
        serverProperties.put("mail.smtp.socketFactory.port", mailProps.getProperty("mail.smtp.socketFactory.port"));
        serverProperties.put("mail.smtp.socketFactory.class", mailProps.getProperty("mail.smtp.socketFactory.class"));
        serverProperties.put("mail.smtp.auth", mailProps.getProperty("mail.smtp.auth"));
        serverProperties.put("mail.smtp.port", mailProps.getProperty("mail.smtp.port"));
        return serverProperties;
    }
}


