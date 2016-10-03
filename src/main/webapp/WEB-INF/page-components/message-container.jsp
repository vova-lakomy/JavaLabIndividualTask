<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input id="should-show-message" class="jlab-hidden" value="${showMessage}">
<% request.getSession().setAttribute("showMessage","false");%>
<div id="message-overlay" class="jlab-message-overlay jlab-fade">
    <div id="message-container" class="jlab-message-container">
        <div>
            ${labels.get(messageKey)}
        </div>
        <div>
            <button id="close-message" class="jlab-close-message-button" type="button">close</button>
        </div>
    </div>
</div>