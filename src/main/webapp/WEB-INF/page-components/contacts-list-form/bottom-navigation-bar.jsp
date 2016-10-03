<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${!(searchQueryString eq null)}">
        <c:set var="hrefString" value="${searchQueryString}"/>
    </c:when>
    <c:otherwise>
        <c:set var="hrefString" value="${'list?page='}"/>
    </c:otherwise>
</c:choose>
<div>
    <div class="jlab-row">
        <div class="jlab-cell-12 jlab-table-bottom-navigation-bar">
            <div class="jlab-button-block jlab-pull-right">
                <c:choose>
                    <c:when test="${(currentPage - 1) <= 0}">
                        <button class="jlab-button" disabled> << </button>
                    </c:when>
                    <c:otherwise>
                        <a class="jlab-button" href="${hrefString}${currentPage - 1}"> << </a>
                    </c:otherwise>
                </c:choose>
                <span id="jlab-page-selector" class="jlab-button">
                ${labels.get('page')} ${currentPage} ${labels.get('page.of')} ${numberOfPages}
                </span>
                <input id="page-selector-input" class="jlab-button jlab-page-select jlab-hidden" type="text" placeholder="${labels.get('page')}">
                <a id="page-selector-go-button" class="jlab-button jlab-page-select-go jlab-hidden" type="button" href="${hrefString}${currentPage}">OK</a>
                <c:choose>
                    <c:when test="${(currentPage + 1) > numberOfPages}">
                        <button class="jlab-button" disabled> >> </button>
                    </c:when>
                    <c:otherwise>
                        <a class="jlab-button" href="${hrefString}${currentPage + 1}"> >> </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>


</div>
