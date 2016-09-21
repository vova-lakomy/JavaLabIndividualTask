<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div class="jlab-row">
        <div class="jlab-cell-12 jlab-table-bottom-navigation-bar">
            <div class="jlab-button-block jlab-pull-right">
                <a class="jlab-button" href="list?page=${currentPage - 1}"> << </a>
                <span class="jlab-button">page ${currentPage}</span>
                <a class="jlab-button" href="list?page=${currentPage + 1}"> >> </a>
            </div>
        </div>
    </div>


</div>
