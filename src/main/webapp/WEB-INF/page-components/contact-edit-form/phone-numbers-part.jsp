<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="phone-number-caption" class="jlab-row">
    <div class="jlab-cell-12 jlab-inner-table-caption">
        <span>${labels.get('inner.caption.phone.numbers')}:</span>
        <div class="jlab-pull-right jlab-button-block">
            <button id="button-show-phone-number-modal" type="button" class="jlab-button">${labels.get('add.new')}</button>
            <button id="button-delete-phone-number" type="button" class="jlab-button" >${labels.get('delete')}</button>
        </div>
    </div>

</div>

<c:choose>
    <c:when test="${fullContact.phoneNumbers != null}">
        <div id="inner-phone-number-table" class="jlab-inner-table-container">
            <ul class="jlab-row jlab-inner-table-column-caption">
                <li class="jlab-cell-4">${labels.get('inner.caption.phone.number')}</li>
                <li class="jlab-cell-2">${labels.get('inner.caption.phone.type')}</li>
                <li class="jlab-cell-6">${labels.get('comment')}</li>
            </ul>
            <div id="phone-number-rows">
                <c:forEach var="phoneNumber" items="${fullContact.phoneNumbers}" varStatus="counter">
                    <ul class="jlab-row">
                        <li class="jlab-cell-4">
                            <input class="jlab-hidden" type="text" name="phoneNumberId" value="${phoneNumber.id}">
                            <input class="jlab-hidden" type="text" name="countryCode" value="${phoneNumber.countryCode}">
                            <input class="jlab-hidden" type="text" name="operatorCode" value="${phoneNumber.operatorCode}">
                            <input class="jlab-hidden" type="text" name="number" value="${phoneNumber.number}">
                            <input class="jlab-hidden" type="text" name="phoneType"  value="${phoneNumber.type}">
                            <input class="jlab-hidden" type="text" name="comment" value="${phoneNumber.comment}">

                            <input type="checkbox" name="selectedId" value="${phoneNumber.id}" id="phoneNumberId-${counter.index}" data-action="deletePhone" >
                            <label for="phoneNumberId-${counter.index}">${phoneNumber.fullNumber}&nbsp;&nbsp;</label>
                            <img class="jlab-edit-image" src="../resources/img/pencil_12x12.png" title="edit" data-action="edit">
                        </li>

                        <li class="jlab-cell-2">
                                ${phoneNumber.type}

                        </li>
                        <li class="jlab-cell-6">
                                ${phoneNumber.comment}
                        </li>
                    </ul>
                </c:forEach>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="phone-numbers-empty-table" class="jlab-row jlab-vertical-padding-10">
            <div class="jlab-cell-12">
                <i>${labels.get('nothing.to.display')}<a href="javascript:toggleClass($('#modal-phone-add'),'jlab-fade')">${labels.get('add')}</a></i>  <%// TODO: 21.09.16 add listener in js %>
            </div>
        </div>
        <div id="inner-phone-number-table" class="jlab-inner-table-container jlab-hidden">
            <ul class="jlab-row jlab-inner-table-column-caption">
                <li class="jlab-cell-4">${labels.get('inner.caption.phone.number')}</li>
                <li class="jlab-cell-2">${labels.get('inner.caption.phone.type')}</li>
                <li class="jlab-cell-6">${labels.get('comment')}</li>
            </ul>
            <div id="phone-number-rows">

            </div>
        </div>
    </c:otherwise>
</c:choose>

