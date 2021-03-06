package com.javalab.contacts.util;


import com.javalab.contacts.exception.SendingMailException;
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
    public static final Boolean USE_HTML_TRUE = true;
    public static final Boolean USE_HTML_FALSE = false;
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    private Properties mailProps = PropertiesProvider.getInstance().getMailProperties();

    public void sendMail(Session mailSession, Address address, String mailSubject, String messageText, Boolean useHTML) throws SendingMailException {

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(mailProps.getProperty("mail.from")));
            message.setReplyTo(InternetAddress.parse(mailProps.getProperty("mail.from")));
            message.setRecipient(Message.RecipientType.TO, address);
            message.setSubject(mailSubject, "utf-8");
            if (useHTML){
                message.setContent(messageText, "text/html; charset=utf-8");
            } else {
                message.setContent(messageText, "text/plain; charset=utf-8");
            }
            Transport.send(message);
            logger.info("sending e-mail to {} done" ,address);
        } catch (MessagingException e) {
            logger.error("there was an exception while sending mail {}",e);
            throw new SendingMailException(e.getMessage());
        }

    }

    public Session createMailSession(){
        return getMailSession(getMailServerProperties());
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

    public void sendMailToAdministrator(String mailSubject, String messageText) throws SendingMailException {
        String administratorAddressString = mailProps.getProperty("administrator.mail");
        try {
            Address administratorAddress = new InternetAddress(administratorAddressString);
            sendMail(createMailSession(), administratorAddress, mailSubject, messageText, USE_HTML_TRUE);
        } catch (AddressException e) {
            logger.error("failed to create address from string {}", administratorAddressString);
        }
    }
}


