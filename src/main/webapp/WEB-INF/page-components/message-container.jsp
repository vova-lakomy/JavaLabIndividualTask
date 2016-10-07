<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input id="should-show-message" class="jlab-hidden" value="${showMessage}">
<% request.getSession().setAttribute("showMessage","false");%>
<div id="message-overlay" class="jlab-message-overlay jlab-fade">
    <div id="message-container" class="jlab-message-container">
        <div>
            <c:choose>
                <c:when test="${not (labels.get(messageKey) eq null)}">
                    ${labels.get(messageKey)}
                </c:when>
                <c:otherwise>
                    ${messageKey}
                </c:otherwise>
            </c:choose>
        </div>
        <div>
            <button id="close-message" class="jlab-close-message-button" type="button">${labels.get('close')}</button>
        </div>
    </div>
</div>