package com.javalab.contacts.service.command;


import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.exception.EntityNotFoundException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import com.javalab.contacts.util.CustomReflectionUtil;
import com.javalab.contacts.util.LabelsManager;
import com.javalab.contacts.util.MailSender;
import com.javalab.contacts.util.STemplates;
import com.javalab.contacts.util.UiMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.trim;

public class MailCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(MailCommand.class);
    private MailSender mailSender = new MailSender();
    private ContactRepository repository = new ContactRepositoryImpl();
    private STemplates stringTemplates = STemplates.getInstance();
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Mail Command");
        labelsManager.setLocaleLabelsToSession(request.getSession());
        logger.debug("retrieving mail templates");
        Map<String, String> templates = stringTemplates.getTemplateMap();
        request.setAttribute("templates", templates);
        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            logger.debug("found {} contacts to send mail", selectedIds.length);
            Collection<ContactShortDTO> contactShortDTOs = new ArrayList<>();
            String failedContacts = "";
            for (String stringId : selectedIds){
                stringId = trim(stringId);
                Integer id;
                if(isNumeric(stringId)){
                    id = Integer.parseInt(stringId);
                    ContactShortDTO contactShortDTO = null;
                    try {
                        logger.debug("getting short info about contact with id ={}", id);
                        contactShortDTO = repository.getContactShortDTO(id);
                        logger.debug("got contact short info");
                    } catch (ConnectionFailedException e) {
                        UiMessageService.sendConnectionErrorMessageToUI(request, response);
                    } catch (EntityNotFoundException e) {
                        UiMessageService.sendContactNotFoundErrorToUI(request, response);
                    }
                    if (contactShortDTO != null && contactShortDTO.geteMail() == null){
                        String contactName = contactShortDTO.getFullName().replace("<br/>", " ");
                        failedContacts += contactName + ",<br>";
                        logger.error("contact with id={} has no email address");
                    }else {
                        contactShortDTOs.add(contactShortDTO);
                    }
                }
            }
            if (isNotBlank(failedContacts)){
                UiMessageService.prepareEmptyEmailPopUpErrorMessage(request, failedContacts);
            }
            request.setAttribute("emailContacts", contactShortDTOs);
            logger.debug("execution Mail command end");
            return "contact-email-form.jsp";
        } else if (request.getParameterValues("mailTo") != null) {
            logger.debug("trying to send emails");
            try {
                sendMails(request);
                logger.debug("emails were sent");
            } catch (ConnectionFailedException e) {
                UiMessageService.sendConnectionErrorMessageToUI(request, response);
            } catch (EntityNotFoundException e) {
                UiMessageService.sendContactNotFoundErrorToUI(request, response);
            }
            UiMessageService.prepareEmailsSentPopUpInfoMessage(request);
            logger.debug("execution Mail command end");
            return "";
        } else {
            logger.debug("execution Mail command end");
            return "contact-email-form.jsp";
        }
    }

    private void sendMails(HttpServletRequest request) throws ConnectionFailedException, EntityNotFoundException {
        logger.debug("defining mail parameters");
        String mailSubject = request.getParameter("mailSubject");
        if (isBlank(mailSubject)) {
            mailSubject = "(No subject)";
        }
        logger.debug("creating messages map");
        Map<Address, String> messageMap = mapMessagesToAddresses(request);
        logger.debug("message map created.. retrieving session");
        Session mailSession = mailSender.createMailSession();
        logger.debug("session got... sending mails");
        for (Map.Entry<Address, String> entry : messageMap.entrySet()) {
            Address address = entry.getKey();
            String messageText = entry.getValue();
            logger.debug("sending mail to {}", address.toString());
            mailSender.sendMail(mailSession, address, mailSubject, messageText, MailSender.USE_HTML_FALSE);
        }
    }

    private Map<Address, String> mapMessagesToAddresses(HttpServletRequest request) throws ConnectionFailedException, EntityNotFoundException {
        logger.debug("mapping messages to addresses");
        Map<Address, String> resultMap = new HashMap<>();
        Map<String, Integer> addressesMapFromRequest = getAddressesMapFromRequest(request);
        for (Map.Entry<String, Integer> entry : addressesMapFromRequest.entrySet()) {
            Address address = null;
            try {
                String stringAddress = entry.getKey();
                address = new InternetAddress(stringAddress);
                logger.debug("created internet address {}",address);
            } catch (AddressException e) {
                logger.error("{}", e);
            }
            Integer contactId = entry.getValue();
            String message = defineMailMessage(request, contactId);
            resultMap.put(address, message);
        }
        return resultMap;
    }

    private String defineMailMessage(HttpServletRequest request, Integer contactId) throws ConnectionFailedException, EntityNotFoundException {
        logger.debug("creating message body");
        String message = request.getParameter("mailText");
        if (isBlank(message)) {
            message = "";
        } else if (contactId != null) {
            List<String> templateParams = defineTemplateParams(message);
            if (templateParams.size() > 0){
                logger.debug("creating text template");
                ST template = createStringTemplate(message);
                logger.debug("defining fields of 'Contact' instance as StringTemplate parameters");
                Map<String, Object> parameterMap = definePossibleParamValues(contactId);
                templateParams.forEach(param -> {
                    Object paramValue = parameterMap.get(param);
                    if (paramValue != null) {
                        template.add(param, paramValue);
                    } else {
                        logger.error("failed to find parameter {}",param);
                    }
                });
                logger.debug("rendering message from template");
                message = template.render();
            }
        }
        return message;
    }

    private Map<String, Object> definePossibleParamValues(Integer contactId) throws ConnectionFailedException, EntityNotFoundException {
        ContactFullDTO contact = repository.getContactFullInfo(contactId);
        return CustomReflectionUtil.getObjectFieldsWithValues(contact);
    }

    private ST createStringTemplate(String message) {
        logger.debug("creating custom StringTemplate");
        String templateName = "customTemplate";
        STGroup stGroup = stringTemplates.getTemplatesGroup();
        List<String> templateParams = defineTemplateParams(message);
        StringBuilder oneStringParams = new StringBuilder();
        templateParams.forEach(param -> {
            oneStringParams.append(param);
            oneStringParams.append(',');
        });
        stGroup.defineTemplate(templateName,oneStringParams.toString(),message);
        logger.debug("template defined");
        return stGroup.getInstanceOf(templateName);
    }

    private List<String> defineTemplateParams(String message) {
        List<String> params = new ArrayList<>();
        Matcher matcher = Pattern.compile("%\\w+?%").matcher(message);
        while (matcher.find()) {
            params.add(matcher.group().replaceAll("%",""));
        }
        return params;
    }

    private Map<String, Integer> getAddressesMapFromRequest(HttpServletRequest request) {
        logger.debug("retrieving addresses from request");
        Map<String, Integer> emailMap = new HashMap<>();
        String[] mailTo = request.getParameterValues("mailTo");
        String[] mailToIds = request.getParameterValues("mailToId");
        for (int i = 0; i < mailTo.length; i++) {
            Integer contactIdMailTo = null;
            if (isNotBlank(mailToIds[i])) {
                contactIdMailTo = Integer.parseInt(mailToIds[i]);
            }
            if (isNotBlank(mailTo[i])) {
                String mailAddress = mailTo[i].trim();
                emailMap.put(mailAddress, contactIdMailTo);
            }
        }
        return emailMap;
    }
}
