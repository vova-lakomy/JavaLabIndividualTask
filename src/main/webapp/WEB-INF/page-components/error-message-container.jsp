<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input id="should-show-error-message"
       class="jlab-hidden"
       value="${showErrorMessage}">
<% request.getSession().setAttribute("showErrorMessage","false");%>

<div id="error-message-overlay"
     class="jlab-message-overlay jlab-fade">
    <div id="error-message-container"
         class="jlab-error-message-container">
        <div class="jlab-error-message-caption">ERROR</div>
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
            <button id="close-error-message"
                    class="jlab-close-message-button"
                    type="button">${labels.get('close')}</button>
        </div>
    </div>
</div>
