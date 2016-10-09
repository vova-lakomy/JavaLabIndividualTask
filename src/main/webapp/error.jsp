<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.getSession().setAttribute("showErrorMessage","false");%>
<% request.getSession().setAttribute("showMessage","false");%>
<jsp:include page="WEB-INF/page-components/header.jsp"/>
    <div class="jlab-error-page">
        <c:choose>
            <c:when test="${(message eq '') || (message eq null)}">
                <h3>Error ${requestScope.get('javax.servlet.error.status_code')}</h3>
                <h3>${requestScope.get('javax.servlet.error.request_uri')}</h3>
                <h3>${requestScope.get('javax.servlet.error.message')}</h3>
                <h3>${requestScope.get('javax.servlet.error.exception')}</h3>
            </c:when>
            <c:otherwise>
                <h3>${labels.get('error')}</h3>
                <h3>${message}</h3>
            </c:otherwise>
        </c:choose>
        <i>back to <a href="../index.jsp" target="_self">main page</a></i>
    </div>
<jsp:include page="WEB-INF/page-components/footer.jsp"/>