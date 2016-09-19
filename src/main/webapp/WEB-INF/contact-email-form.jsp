<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-form-container jlab-email-form">
            <h4>Send email</h4>
            <form action="mail" method="post">

                <c:choose>
                    <c:when test="${emailContacts eq null}">
                        <div class="jlab-form-item jlab-row">
                            <label class="jlab-cell-1"
                                   for="messageRecipient">To:</label>
                            <input class="jlab-cell-12" id="messageRecipient" type="text" name="mailTo"
                                   value="" placeholder="message recipient" required data-validation-type="email"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="contact" items="${emailContacts}" varStatus="counter">
                            <div class="jlab-form-item jlab-row">
                                <label class="jlab-cell-1"
                                       for="messageRecipient${counter.index}">To: </label>
                                <input class="jlab-cell-12" id="messageRecipient${counter.index}" type="text"
                                       name="mailTo"
                                       value="${contact.eMail}"
                                       placeholder="message recipient" required/>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

                <div class="jlab-form-item jlab-row">
                    <label class="jlab-cell-1" for="mailSubject">Topic :</label>
                    <input class="jlab-cell-12" id="mailSubject" type="text" placeholder="subject" name="mailSubject">
                </div>

                <div class="jlab-row">
                    <label class="jlab-cell-1" for="mailText">message:</label>
                    <textarea class="jlab-cell-11" id="mailText" name="mailText"
                              placeholder="select template or type text here"></textarea>
                </div>

                <div class="jlab-button-block jlab-vertical-padding-10 jlab-pull-right">
                    <a type="submit" class="jlab-button" href="list">cancel</a>
                    <button type="submit" class="jlab-button">send</button>
                </div>
            </form>
        </div>
    </div>
</div>
