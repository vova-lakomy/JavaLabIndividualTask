<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-main-content-container">
    <form class="jlab-main-content jlab-main-list" action="deleteContact" data-form-name="contactForm">
        <jsp:include page="page_components/contacts_list_form/top-navigation-bar.jsp"/>
        <ul>
            <li>
                <jsp:include page="page_components/contacts_list_form/contact-table-caption.jsp"/>
            </li>
            <c:forEach items="${contacts}" var="contactItem">
                <c:set var="contact" value="${contactItem}" scope="request"/>
                <jsp:include page="page_components/contacts_list_form/contact-row.jsp"/>
            </c:forEach>
            <li>
                <jsp:include page="page_components/contacts_list_form/bottom-navigation-bar.jsp"/>
            </li>
        </ul>
    </form>
</div>

