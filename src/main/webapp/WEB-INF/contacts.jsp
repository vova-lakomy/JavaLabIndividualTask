<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="page_components/header.jsp"/>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <jsp:include page="page_components/contacts_list_table/top-navigation-bar.jsp"/>
        <ul>
            <li>
                <jsp:include page="page_components/contacts_list_table/contact-table-caption.jsp"/>
            </li>
            <c:forEach items="${contacts}" var="contactItem">
                <c:set var="contact" value="${contactItem}" scope="request"/>
                <jsp:include page="page_components/contacts_list_table/contact-row.jsp"/>
            </c:forEach>
            <li>
                <jsp:include page="page_components/contacts_list_table/bottom-navigation-bar.jsp"/>
            </li>
        </ul>
    </div>
</div>

<jsp:include page="page_components/footer.jsp"/>
