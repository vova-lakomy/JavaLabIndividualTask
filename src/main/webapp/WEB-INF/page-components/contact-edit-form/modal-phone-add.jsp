<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="modal-phone-add"
     class="jlab-modal-container jlab-fade">
    <form id="phone-add-form"
          class="jlab-modal jlab-phone-number-edit-form"
          data-form-name="addPhoneModal">
        <h4>${labels.get('caption.add.phone')}</h4>
        <div class="jlab-form-item">
            <input class="jlab-hidden"
                   name="phoneNumberId"
                   value="">
            <label for="phone-number-country-code">${labels.get('country.code')}</label>
            <input id="phone-number-country-code"
                   name="countryCode"
                   type="text"
                   value=""
                   maxlength="3"
                   placeholder="${labels.get('country.code.placeholder')}"
                   data-validation-type="countryCode">
        </div>
        <div class="jlab-form-item">
            <label for="phone-number-operator-code">${labels.get('operator.code')}</label>
            <input id="phone-number-operator-code"
                   name="operatorCode"
                   type="text"
                   value=""
                   maxlength="4"
                   placeholder="${labels.get('operator.code.placeholder')}"
                   data-validation-type="operatorCode">
        </div>
        <div class="jlab-form-item">
            <label for="phone-number">${labels.get('phone.number')}</label>
            <input id="phone-number"
                   name="phoneNumber"
                   type="text"
                   value=""
                   maxlength="9"
                   placeholder="${labels.get('phone.number.placeholder')}"
                   data-validation-type="phoneNumber">
        </div>
        <div class="jlab-form-item">
            <label for="phone-type-select">${labels.get('phone.type')}</label>
            <select id="phone-type-select"
                    name="phoneType">
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
            <textarea id="phone-number-comment"
                      class="jlab-comment-textarea"
                      name="phoneComment"
                      placeholder="${labels.get('comment.placeholder')}"
                      maxlength="1000"
                      data-validation-type="comment"></textarea>
        </div>

        <div class="jlab-pull-right jlab-button-block ">
            <button id="button-add-phone-number"
                    type="submit"
                    class="jlab-button">${labels.get('save')}</button>
            <button id="button-cancel-phone-number-add"
                    type="button"
                    class="jlab-button">${labels.get('cancel')}</button>
        </div>

    </form>
</div>
