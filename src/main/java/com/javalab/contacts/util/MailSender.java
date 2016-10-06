package com.javalab.contacts.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class MailSender {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    private Properties mailProps = PropertiesProvider.getInstance().getMailProperties();

    public void sendMail(Address address, String mailSubject, String messageText) {

        try {
            Message message = new MimeMessage(getMailSession(getMailServerProperties()));
            message.setFrom(new InternetAddress(mailProps.getProperty("mail.from")));
            message.setReplyTo(InternetAddress.parse(mailProps.getProperty("mail.from")));
            message.setRecipient(Message.RecipientType.TO, address);
            message.setSubject(mailSubject);
//            message.setContent(messageText,"text/html;charset=utf-8");
            message.setText(messageText);
            Transport.send(message);
            logger.info("sending e-mail to {} done" ,address);
        } catch (MessagingException e) {
            logger.error("there was an exception while sending mail {}",e);
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

    public void sendMailToAdministrator(String mailSubject, String messageText){
        String administratorAddressString = mailProps.getProperty("administrator.mail");
        try {
            Address administratorAddress = new InternetAddress(administratorAddressString);
            sendMail(administratorAddress, mailSubject, messageText);
        } catch (AddressException e) {
            logger.error("failed to create address from string {}", administratorAddressString);
        }
    }
}


