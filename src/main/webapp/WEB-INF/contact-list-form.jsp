<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-main-content-container">
    <form class="jlab-main-content jlab-main-list" method="post" data-form-name="contactForm">
        <jsp:include page="page-components/contacts-list-form/top-navigation-bar.jsp"/>
        <ul>
            <li>
                <jsp:include page="page-components/contacts-list-form/contact-table-caption.jsp"/>
            </li>
            <c:choose>
                <c:when test="${!(searchResult eq null)}">
                    <c:set var="contactItems" value="${searchResult}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="contactItems" value="${contactsList}"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${contactItems.size() > 0}">
                    <c:forEach items="${contactItems}" var="contactItem">
                        <c:set var="contact" value="${contactItem}" scope="request"/>
                        <jsp:include page="page-components/contacts-list-form/contact-row.jsp"/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div id="no-contacts-to-display">
                            ${labels.get('no.contacts.to.display')}
                    </div>
                </c:otherwise>
            </c:choose>

            <li>
                <jsp:include page="page-components/contacts-list-form/bottom-navigation-bar.jsp"/>
            </li>
        </ul>
    </form>
</div>
<script type="text/javascript" src="${rootContext}resources/js/paginator.js"></script>

