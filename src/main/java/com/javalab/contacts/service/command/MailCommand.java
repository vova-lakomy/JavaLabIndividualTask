package com.javalab.contacts.service.command;


import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
import com.javalab.contacts.util.CustomReflectionUtil;
import com.javalab.contacts.util.MailSender;
import com.javalab.contacts.util.STemplates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private ContactDtoRepository repository = new ContactDtoRepositoryImpl();
    private STemplates stringTemplates = STemplates.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> templates = stringTemplates.getTemplateMap();
        request.setAttribute("templates", templates);

        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            Collection<ContactShortDTO> contactShortDTOs = new ArrayList<>();
            for (String stringId : selectedIds){
                stringId = trim(stringId);
                Integer id;
                if(isNumeric(stringId)){
                    id = Integer.parseInt(stringId);
                    ContactShortDTO contactShortDTO = repository.getContactShortDTO(id);
                    contactShortDTOs.add(contactShortDTO);
                }
            }
            request.setAttribute("emailContacts", contactShortDTOs);
            request.setAttribute("path", "contact-email-form.jsp");
        } else if (request.getParameterValues("mailTo") != null) {
            sendMails(request);
            request.getSession().setAttribute("messageKey","message.email.sent");
            request.getSession().setAttribute("showMessage","true");
            try {
                response.sendRedirect("list");
                return;
            } catch (IOException e) {
                logger.error("{}",e);
            }
        } else {
            request.setAttribute("path", "contact-email-form.jsp");
        }

        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("{}",e);
        }
    }

    private void sendMails(HttpServletRequest request) {
        String mailSubject = request.getParameter("mailSubject");
        if (isBlank(mailSubject)) {
            mailSubject = "(No subject)";
        }
        Map<Address, String> messageMap = mapMessagesToAddresses(request);
        for (Map.Entry<Address, String> entry : messageMap.entrySet()) {
            Address address = entry.getKey();
            String messageText = entry.getValue();
            mailSender.sendMail(address, mailSubject, messageText);
        }
    }

    private Map<Address, String> mapMessagesToAddresses(HttpServletRequest request) {
        Map<Address, String> resultMap = new HashMap<>();
        Map<String, Integer> addressesMapFromRequest = getAddressesMapFromRequest(request);
        for (Map.Entry<String, Integer> entry : addressesMapFromRequest.entrySet()) {
            Address address = null;
            try {
                String stringAddress = entry.getKey();
                address = new InternetAddress(stringAddress);
            } catch (AddressException e) {
                logger.error("{}", e);
            }
            Integer contactId = entry.getValue();
            String message = defineMailMessage(request, contactId);
            resultMap.put(address, message);
        }
        return resultMap;
    }

    private String defineMailMessage(HttpServletRequest request, Integer contactId) {
        String message = request.getParameter("mailText");
        if (isBlank(message)) {
            message = "";
        } else if (contactId != null) {
            List<String> templateParams = defineTemplateParams(message);
            if (templateParams.size() > 0){
                ST template = createStringTemplate(message);
                Map<String, Object> parameterMap = definePossibleParamValues(contactId);
                templateParams.forEach(param -> {
                    Object paramValue = parameterMap.get(param);
                    if (paramValue != null) {
                        template.add(param, paramValue);
                    } else {
                        logger.error("failed to find parameter {}",param);
                    }
                });
                message = template.render();
            }
        }
        return message;
    }

    private Map<String, Object> definePossibleParamValues(Integer contactId) {
        ContactFullDTO contact = repository.getContactFullInfo(contactId);
        return CustomReflectionUtil.getObjectFieldsWithValues(contact);
    }

    private ST createStringTemplate(String message) {
        String templateName = "customTemplate";
        STGroup stGroup = stringTemplates.getTemplatesGroup();
        List<String> templateParams = defineTemplateParams(message);
        StringBuilder oneStringParams = new StringBuilder();
        templateParams.forEach(param -> {
            oneStringParams.append(param);
            oneStringParams.append(',');
        });
        stGroup.defineTemplate(templateName,oneStringParams.toString(),message);
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
