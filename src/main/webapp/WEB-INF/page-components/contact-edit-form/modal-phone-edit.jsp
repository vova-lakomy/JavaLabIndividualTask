<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="modal-phone-edit" class="jlab-modal-container jlab-fade">
    <form id="phone-edit-form" class="jlab-modal jlab-phone-number-edit-form" data-form-name="phone-edit-form">
        <h4>${labels.get('edit.phone')}</h4>
        <div class="jlab-form-item">
            <input class="jlab-hidden" name="phoneNumberCounter" value="">
            <input class="jlab-hidden" name="phoneNumberId" value="">
            <label for="phone-number-country-code">${labels.get('country.code')}</label>
            <input id="phone-number-country-code" name="countryCode" type="text" value=""
                   data-validation-type="countryCode" placeholder="${labels.get('country.code.placeholder')}">
        </div>
        <div class="jlab-form-item">
            <label for="phone-number-operator-code">${labels.get('operator.code')}</label>
            <input id="phone-number-operator-code" name="operatorCode" type="text" value=""
                   data-validation-type="operatorCode" placeholder="${labels.get('operator.code.placeholder')}">
        </div>
        <div class="jlab-form-item">
            <label for="phone-number">${labels.get('phone.number')}</label>
            <input id="phone-number" name="phoneNumber" type="text" value=""
                   data-validation-type="phoneNumber" placeholder="${labels.get('phone.number.placeholder')}">
        </div>
        <div class="jlab-form-item">
            <label for="phone-type-select">${labels.get('phone.type')}</label>
            <select id="phone-type-select" name="phoneType">
                <c:forEach items="${phoneTypeList}" var="phoneType">
                    <c:choose>
                        <c:when test="${value eq null}">
                            <option selected value="${phoneType}">${labels.get(phoneType)}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${phoneType}">${labels.get(phoneType)}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>

        <div class="jlab-form-item">
            <label for="phone-number-comment">${labels.get('comment')}</label>
            <input id="phone-number-comment" name="phoneComment" type="text" value="" placeholder="${labels.get('comment.placeholder')}">
        </div>

        <div class="jlab-pull-right jlab-button-block ">
            <button id="button-save-phone-number" type="submit" class="jlab-button">${labels.get('save')}</button>
            <button id="button-cancel-phone-number-edit" type="button" class="jlab-button">${labels.get('cancel')}</button>
        </div>

    </form>
</div>
