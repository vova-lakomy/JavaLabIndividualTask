<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="application/javascript"
        src="${rootContext}resources/js/validator.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="page-components/message-container.jsp"/>
<jsp:include page="page-components/error-message-container.jsp"/>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-form-container jlab-email-form">
            <h4>${labels.get('caption.send.email')}</h4>
            <form id="email-form"
                  action="mail"
                  method="post"
                  data-form-name="email-form">
                <c:choose>
                    <c:when test="${emailContacts eq null}">
                        <div class="jlab-form-item jlab-row">
                            <label class="jlab-cell-1"
                                   for="messageRecipient">
                                   ${labels.get('mail.to')}:<span class="jlab-required">*</span>
                            </label>
                            <input class="jlab-cell-11"
                                   id="messageRecipient"
                                   type="text"
                                   name="mailTo"
                                   value=""
                                   placeholder="${labels.get('mail.to.placeholder')}"
                                   required data-validation-type="email"/>
                            <input class="jlab-hidden"
                                   name="mailToId"
                                   value="${contact.id}">
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="contact" items="${emailContacts}" varStatus="counter">
                            <div class="jlab-form-item jlab-row">
                                <label class="jlab-cell-1"
                                       for="messageRecipient${counter.index}">
                                       ${labels.get('mail.to')}:<span class="jlab-required">*</span>
                                </label>
                                <input class="jlab-cell-11"
                                       id="messageRecipient${counter.index}"
                                       type="text"
                                       name="mailTo"
                                       value="${contact.eMail}"
                                       placeholder="${labels.get('mail.to.placeholder')}"
                                       required/>
                                <input class="jlab-hidden"
                                       name="mailToId"
                                       value="${contact.id}">
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

                <div class="jlab-form-item jlab-row">
                    <label class="jlab-cell-1"
                           for="mailSubject">${labels.get('mail.subject')}:</label>
                    <input class="jlab-cell-11"
                           id="mailSubject"
                           type="text"
                           placeholder="${labels.get('mail.subject.placeholder')}"
                           name="mailSubject">
                </div>

                <div class="jlab-form-item jlab-row">
                    <label class="jlab-cell-1"
                           for="templateSelect">${labels.get('mail.template')} :</label>
                    <select class="jlab-cell-11"
                            id="templateSelect"
                            name="selectedTemplate">
                        <option value="">${labels.get('none')}</option>
                        <c:forEach var="template" items="${templates}">
                            <option value="${template.key}">${template.key}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="jlab-row">
                    <label class="jlab-cell-1"
                           for="mailText">${labels.get('mail.message')}:</label>
                    <textarea class="jlab-cell-11"
                              id="mailText"
                              name="mailText"
                              placeholder="${labels.get('mail.message.placeholder')}"></textarea>
                </div>

                <div class="jlab-button-block jlab-vertical-padding-10 jlab-pull-right">
                    <a class="jlab-button"
                       href="list">${labels.get('cancel')}</a>
                    <button type="submit"
                            class="jlab-button">${labels.get('mail.send')}</button>
                </div>
            </form>
        </div>
    </div>
    <c:forEach var="template" items="${templates}">
       <textarea id="${template.key}"
                 class="jlab-hidden">${template.value}</textarea>
    </c:forEach>

</div>
<script type="text/javascript"
        src="${rootContext}resources/js/contact-email-form.js"></script>