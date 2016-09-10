<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="page_components/header.jsp"/>

<c:choose>
    <c:when test="${path == 'list'}">
        <jsp:include page="contacts.jsp"/>
    </c:when>
    <c:when test="${path == 'edit'}">
        <jsp:include page="contact-edit-form.jsp"/>
    </c:when>

</c:choose>

<jsp:include page="page_components/footer.jsp"/>
