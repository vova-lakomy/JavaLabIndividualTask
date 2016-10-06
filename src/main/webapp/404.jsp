<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="WEB-INF/page-components/header.jsp"/>
<div class="jlab-page-not-found">
    <h3>${labels.get('requested.url.not.found')}</h3>
    <i>${labels.get('back.to')} <a href="${rootContext}index.jsp" target="_self">${labels.get('to.main.page')}</a></i>
</div>
<jsp:include page="WEB-INF/page-components/footer.jsp"/>