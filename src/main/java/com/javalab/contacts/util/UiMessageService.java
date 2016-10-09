package com.javalab.contacts.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public final class UiMessageService {
    private UiMessageService(){
    }
    private static final Logger logger = LoggerFactory.getLogger(UiMessageService.class);
    private static LabelsManager labelsManager = LabelsManager.getInstance();

    private static String createSaveContactErrorMessage(String localeKey) {
        Map<String, String> labels = labelsManager.getLabels(localeKey);
        return labels.get("message.failed.save.contact");
    }

    private static String createContactNotFoundErrorMessage(String localeKey) {
        Map<String, String> labels = labelsManager.getLabels(localeKey);
        return labels.get("message.contact.not.found");
    }

    private static String createFileSaveErrorMessage(String localeKey) {
        Map<String, String> labels = labelsManager.getLabels(localeKey);
        return labels.get("message.photo.save.failed");
    }

    private static String createConnectionFailedMessage(String localeKey) {
        Map<String, String> labels = labelsManager.getLabels(localeKey);
        return labels.get("message.connection.failed");
    }

    private static String createDirectoryAccessFailedMessage(String localeKey) {
        Map<String, String> labels = labelsManager.getLabels(localeKey);
        return labels.get("message.dir.access.failed");
    }

    private static String createEmptyFieldMessage(String localeKey) {
        Map<String, String> labels = labelsManager.getLabels(localeKey);
        return labels.get("message.field.is.empty");
    }

    private static String createAttachmentFailedMessage(String localeKey) {
        Map<String, String> labels = labelsManager.getLabels(localeKey);
        return labels.get("message.process.attachment.fail");
    }

    private static String createEmailErrorMessage(String failedContacts, String localeKey) {
        Map<String, String> labels = labelsManager.getLabels(localeKey);
        failedContacts = failedContacts.substring(0, failedContacts.lastIndexOf(',')) + "<br>";
        String message = labels.get("message.no.email.begin") + "<br>" +
                failedContacts +
                labels.get("message.no.email.end");
        return message;
    }

    public static void sendConnectionErrorMessageToUI(HttpServletRequest request, HttpServletResponse response) {
        try {
            String localeKey = (String) request.getSession().getAttribute("localeKey");
            String errorMessage = createConnectionFailedMessage(localeKey);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMessage);
        } catch (IOException e1) {
            logger.error("", e1);
        }
    }

    public static void sendSaveErrorMessageToUI(HttpServletRequest request, HttpServletResponse response) {
        String localeKey = (String) request.getSession().getAttribute("localeKey");
        String errorMessage = createSaveContactErrorMessage(localeKey);
        try {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, errorMessage);
        } catch (IOException e1) {
            logger.error("", e1);
        }
    }

    public static void sendEmptyFieldErrorToUI(HttpServletRequest request, HttpServletResponse response){
        String localeKey = (String) request.getSession().getAttribute("localeKey");
        String errorMessage = createEmptyFieldMessage(localeKey);
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    public static void sendAttachmentProcessErrorToUI(HttpServletRequest request, HttpServletResponse response){
        String localeKey = (String) request.getSession().getAttribute("localeKey");
        String errorMessage = createAttachmentFailedMessage(localeKey);
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    public static void sendPhotoProcessErrorToUI(HttpServletRequest request, HttpServletResponse response){
        String localeKey = (String) request.getSession().getAttribute("localeKey");
        String errorMessage = createFileSaveErrorMessage(localeKey);
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    public static void sendDirectoryAccessErrorToUI(HttpServletRequest request, HttpServletResponse response, String dir){
        String localeKey = (String) request.getSession().getAttribute("localeKey");
        String errorMessage = createDirectoryAccessFailedMessage(localeKey) + dir;
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }

    public static void sendContactNotFoundErrorToUI(HttpServletRequest request, HttpServletResponse response){
        String localeKey = (String) request.getSession().getAttribute("localeKey");
        String messageKey = createContactNotFoundErrorMessage(localeKey);
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, messageKey);
        } catch (IOException e1) {
            logger.error("", e1);
        }
    }

    public static void prepareDeleteFailedPopUpErrorMessage(HttpServletRequest request){
        String localeKey = (String) request.getSession().getAttribute("localeKey");
        String errorMessage = createContactNotFoundErrorMessage(localeKey);
        request.getSession().setAttribute("messageKey", errorMessage);
        request.getSession().setAttribute("showErrorMessage","true");
    }

    public static void prepareEmptyEmailPopUpErrorMessage(HttpServletRequest request, String failedContacts){
        String localeKey = (String) request.getSession().getAttribute("localeKey");
        String errorMessage = createEmailErrorMessage(failedContacts, localeKey);
        request.getSession().setAttribute("messageKey", errorMessage);
        request.getSession().setAttribute("showErrorMessage","true");
    }


    public static void prepareEmailsSentPopUpInfoMessage(HttpServletRequest request){
        request.getSession().setAttribute("messageKey","message.email.sent");
        request.getSession().setAttribute("showMessage","true");
    }

    public static void prepareContactSavedPopUpInfoMessage(HttpServletRequest request){
        request.getSession().setAttribute("messageKey","message.contact.saved");
        request.getSession().setAttribute("showMessage","true");
    }
}
