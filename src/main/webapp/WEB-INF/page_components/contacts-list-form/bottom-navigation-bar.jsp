<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div class="jlab-row">
        <div class="jlab-cell-12 jlab-table-bottom-navigation-bar">
            <div class="jlab-button-block jlab-pull-right">
                <c:choose>
                    <c:when test="${(currentPage - 1) == 0}">
                        <button class="jlab-button" disabled> << </button>
                    </c:when>
                    <c:otherwise>
                        <a class="jlab-button" href="list?page=${currentPage - 1}"> << </a>
                    </c:otherwise>
                </c:choose>
                <span class="jlab-button">page ${currentPage}</span>
                <a class="jlab-button" href="list?page=${currentPage + 1}"> >> </a>
            </div>
        </div>
    </div>


</div>
