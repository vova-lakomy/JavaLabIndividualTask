<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="modal-photo-upload.jsp"/>

<div class="jlab-row">
        <div class="jlab-cell-12">
            <div id="contact-photo-container" class="jlab-main-photo-container">
                    <c:choose>
                        <c:when test="${(fullContact.photoLink == null) || (fullContact.photoLink eq 'null')}">
                            <img src="${rootContext}/resources/img/unknown_person.png">
                        </c:when>
                        <c:otherwise>
                            <img src="${rootContext}/${fullContact.photoLink}">
                        </c:otherwise>
                    </c:choose>
                <input class="jlab-hidden" type="text" name="photoLink" value="${rootContext}${fullContact.photoLink}">
            </div>
        </div>
    </div>
