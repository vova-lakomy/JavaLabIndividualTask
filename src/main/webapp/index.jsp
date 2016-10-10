<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="currentURL"
       value="list"
       scope="session"/>
<c:set var="currentPage"
       value="1"
       scope="session"/>

<c:choose>
    <c:when test="${localeKey eq null}">
        <c:set var="localeKey"
               value="en"
               scope="session"/>
    </c:when>
</c:choose>

<%String localeKey = (String) request.getSession().getAttribute("localeKey");
    response.sendRedirect("contacts/setLocale?localeKey=" + localeKey); %>

