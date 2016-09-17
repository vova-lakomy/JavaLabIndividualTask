<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="phone-number-caption" class="jlab-row">
    <div class="jlab-cell-12 jlab-inner-table-caption">
        <span>Phone numbers:</span>
        <div class="jlab-pull-right jlab-button-block">
            <button id="button-show-phone-number-modal" type="button" class="jlab-button">add new</button>
            <button type="button" class="jlab-button" disabled>edit</button>
            <button type="button" class="jlab-button" disabled>delete</button>
        </div>
    </div>

</div>

<c:choose>
    <c:when test="${fullContact.phoneNumbers != null}">
        <div id="inner-phone-number-table" class="jlab-inner-table-container">
            <ul class="jlab-row jlab-inner-table-column-caption">
                <li class="jlab-cell-3">Phone number</li>
                <li class="jlab-cell-2">Type</li>
                <li class="jlab-cell-7">Comment</li>
            </ul>
            <div id="phone-number-rows">
                <c:forEach var="phoneNumber" items="${fullContact.phoneNumbers}" varStatus="counter">
                    <ul class="jlab-row">
                        <li class="jlab-cell-3">
                            <input type="checkbox" id="phoneNumberId${counter.index}" >
                            <input class="jlab-hidden" type="text" name="phoneNumberId" value="${phoneNumber.id}">
                            <label for="phoneNumberId${counter.index}">${phoneNumber.fullNumber}</label>
                            <input class="jlab-hidden" type="text" name="countryCode" value="${phoneNumber.countryCode}">
                            <input class="jlab-hidden" type="text" name="operatorCode"
                                   value="${phoneNumber.operatorCode}">
                            <input class="jlab-hidden" type="text" name="number" value="${phoneNumber.number}">
                        </li>

                        <li class="jlab-cell-2">
                                ${phoneNumber.type}
                            <input class="jlab-hidden" type="text" name="phoneType"  value="${phoneNumber.type}">
                        </li>

                        <li class="jlab-cell-7">
                                ${phoneNumber.comment}
                            <input class="jlab-hidden" type="text" name="comment" value="${phoneNumber.comment}">
                        </li>
                    </ul>
                </c:forEach>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="phone-numbers-empty-table" class="jlab-row jlab-vertical-padding-10">
            <div class="jlab-cell-12">
                <i>nothing to display <a href="javascript:toggleClass($('#modal-phone-edit'),'jlab-fade')">add</a></i>
            </div>
        </div>
    </c:otherwise>
</c:choose>

