<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="WEB-INF/page-components/header.jsp"/>
    <div class="jlab-error-page">
        <h3>Error ${requestScope.get('javax.servlet.error.status_code')}</h3>
        <h3>${requestScope.get('javax.servlet.error.message')}</h3>
        <i>back to <a href="../index.jsp" target="_self">main page</a></i>
    </div>

<jsp:include page="WEB-INF/page-components/footer.jsp"/>