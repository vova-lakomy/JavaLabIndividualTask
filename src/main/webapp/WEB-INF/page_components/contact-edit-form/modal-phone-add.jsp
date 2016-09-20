<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="modal-phone-add" class="jlab-modal-container jlab-fade">
    <form id="phone-add-form" class="jlab-modal jlab-phone-number-edit-form">
        <h4>Add phone</h4>
        <div class="jlab-form-item">
            <input class="jlab-hidden" name="phoneNumberId" value="">
            <label for="phone-number-country-code">Country code</label>
            <input id="phone-number-country-code" name="countryCode" type="text" value="" placeholder="country code">
        </div>
        <div class="jlab-form-item">
            <label for="phone-number-operator-code">Operator code</label>
            <input id="phone-number-operator-code" name="operatorCode" type="text" value="" placeholder="operator code">
        </div>
        <div class="jlab-form-item">
            <label for="phone-number">Phone number</label>
            <input id="phone-number" name="phoneNumber" type="text" value="" placeholder="phone number">
        </div>
        <div class="jlab-form-item">
            <label for="phone-type-select">Phone type</label>
            <select id="phone-type-select" name="phoneType">
                <c:forEach items="${phoneTypeList}" var="value">
                    <c:choose>
                        <c:when test="${value eq null}">
                            <option selected value="${value}">${value}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${value}">${value}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>

        <div class="jlab-form-item">
            <label for="phone-number-comment">Comment</label>
            <input id="phone-number-comment" name="phoneComment" type="text" value="" placeholder="comment">
        </div>

        <div class="jlab-pull-right jlab-button-block ">
            <button id="button-add-phone-number" type="button" class="jlab-button">add</button>
            <button id="button-cancel-phone-number-add" type="button" class="jlab-button">cancel</button>
        </div>

    </form>
</div>
