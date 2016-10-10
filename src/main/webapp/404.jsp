<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="WEB-INF/page-components/header.jsp"/>
<c:set var="message" value="${requestScope.get('javax.servlet.error.message')}"/>
<% request.getSession().setAttribute("showErrorMessage","false");%>
<% request.getSession().setAttribute("showMessage","false");%>

<div class="jlab-page-not-found">
    <h3>${labels.get('error')} 404</h3>
    <c:choose>
        <c:when test="${(message eq '') || (message eq null)}">
            <h3>${labels.get('requested.url.not.found')}</h3>
        </c:when>
        <c:otherwise>
            <h3>${message}</h3>
        </c:otherwise>
    </c:choose>

    <i>${labels.get('back.to')}
        <a href="${rootContext}index.jsp"
           target="_self">${labels.get('to.main.page')}</a>
    </i>
</div>

<jsp:include page="WEB-INF/page-components/footer.jsp"/>