<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="modal-photo-upload.jsp"/>

<div class="jlab-row">
        <div class="jlab-cell-12">
            <div id="contact-photo-container" class="jlab-main-photo-container">
                <div id="main-photo-blind" class="jlab-main-photo-blind jlab-fade">
                    <div id="not-submitted-caption">
                        ${labels.get('not.submitted')}
                    </div>
                </div>
                    <c:choose>
                        <c:when test="${(fullContact.photoLink == null) || (fullContact.photoLink eq 'null')}">
                            <img id="contact-image" src="${rootContext}resources/img/unknown_person.png">
                        </c:when>
                        <c:otherwise>
                            <img id="contact-image" src="${rootContext}${fullContact.photoLink}">
                        </c:otherwise>
                    </c:choose>
                <input class="jlab-hidden" type="text" name="photoLink" value="${fullContact.photoLink}">
            </div>
        </div>
    </div>
