<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootContext" value="${pageContext.request.contextPath}" scope="session"/>
<c:set var="labels" value="${labels}" scope="session"/>

<html>
<head>
    <title>${labels.get('title')}</title>
    <link type="text/css" rel="stylesheet" href="${rootContext}/resources/css/main.css" />
    <link type="text/css" rel="stylesheet" href="${rootContext}/resources/css/custom-grid.css" />
    <link type="text/css" rel="stylesheet" href="${rootContext}/resources/css/jlab-buttons.css" />
    <script type="application/javascript" src="${rootContext}/resources/js/dom-utils.js"></script>
</head>
<body>
<header class="jlab-header-panel">
    <a href="${rootContext}/index.jsp">
        <span class="jlab-header-caption-red">J</span><span class="jlab-header-caption-black">avaLab2016</span>
        <span class="jlab-header-caption-red">ContactsList</span>
    </a>
    <a href="setLocale?localeKey=en">EN</a>|
    <a href="setLocale?localeKey=ru">RU</a>
</header>
<div class="jlab-margin-top-70">
</div>

