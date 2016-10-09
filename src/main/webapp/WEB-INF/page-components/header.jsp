<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootContext" value="${pageContext.request.contextPath}/" scope="session"/>
<c:set var="labels" value="${labels}" scope="session"/>
<c:set var="locale" value="${localeKey}"/>
<c:set var="currentURL"
       value="${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}"
       scope="session"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${labels.get('title')}</title>
    <link type="text/css" rel="stylesheet" href="${rootContext}resources/css/main.css"/>
    <link type="text/css" rel="stylesheet" href="${rootContext}resources/css/custom-grid.css"/>
    <link type="text/css" rel="stylesheet" href="${rootContext}resources/css/jlab-buttons.css"/>
    <script type="application/javascript" src="${rootContext}resources/js/dom-utils.js"></script>
</head>
<body>

<header class="jlab-header-panel">
    <a href="${rootContext}index.jsp">
        <span class="jlab-header-caption-red">J</span><span class="jlab-header-caption-black">avaLab2016</span>
        <span class="jlab-header-caption-red">ContactsList</span>
    </a>


    <span class="jlab-locale-select">
           <c:choose>
               <c:when test="${locale eq 'ru'}">
                   <c:set var="ruClass" value="jlab-selected-locale"/>
               </c:when>
               <c:otherwise>
                   <c:set var="enClass" value="jlab-selected-locale"/>
               </c:otherwise>
           </c:choose>
        <a id="localeSelectEn" class="${enClass}" href="${rootContext}contacts/setLocale?localeKey=en">EN</a>
        <span>|</span>
        <a id="localeSelectRu" class="${ruClass}" href="${rootContext}contacts/setLocale?localeKey=ru">RU</a>
    </span>

    <a class="jlab-home-page-link" href="${rootContext}index.jsp">${labels.get('go.home')}</a>

</header>
<div class="jlab-margin-top-70">
</div>


