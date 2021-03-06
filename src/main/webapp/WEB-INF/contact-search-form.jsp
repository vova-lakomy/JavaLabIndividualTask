<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="page-components/message-container.jsp"/>
<jsp:include page="page-components/error-message-container.jsp"/>

<script type="application/javascript"
        src="${rootContext}resources/js/validator.js"></script>
<c:set var="yearNow" value="${now.year +1900}"/>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-form-container">
            <h4>${labels.get('caption.advanced.search')}</h4>
            <form id="contact-search-form"
                  class="jlab-contact-search-form"
                  method="get"
                  data-form-name="search-form">
                <div class="jlab-row">
                    <div class="jlab-cell-6">
                        <div class="jlab-form-item">
                            <label for="search-form-last-name">${labels.get('last.name')}</label>
                            <input id="search-form-last-name"
                                   type="text"
                                   name="lastName"
                                   value=""
                                   placeholder="${labels.get('last.name.placeholder')}"
                                   maxlength="20"
                                   data-validation-type="string20Chars">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-first-name">${labels.get('first.name')}</label>
                            <input id="search-form-first-name"
                                   type="text"
                                   name="firstName"
                                   value=""
                                   placeholder="${labels.get('first.name.placeholder')}"
                                   maxlength="20"
                                   data-validation-type="string20Chars">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-second-name">${labels.get('second.name')}</label>
                            <input id="search-form-second-name"
                                   type="text"
                                   name="secondName"
                                   value=""
                                   placeholder="${labels.get('second.name.placeholder')}"
                                   maxlength="20"
                                   data-validation-type="string20Chars">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-birth-day-after">${labels.get('birth.date.after')}</label>
                            <div class="jlab-date-field"
                                 data-date-block="true">
                                <select class="jlab-date-field-item"
                                        id="search-form-birth-day-after"
                                        title="${labels.get('day')}"
                                        name="dayOfBirthAfter"
                                        data-date-part="day">
                                    <option value="">${labels.get('day')}</option>
                                    <c:forEach var="dayOfMonth" begin="1" end="31">
                                            <option value="${dayOfMonth}">${dayOfMonth}</option>
                                    </c:forEach>
                                </select>
                                <select class="jlab-date-field-item"
                                        id="search-form-birth-month-after"
                                        title="${labels.get('month')}"
                                        name="monthOfBirthAfter"
                                        data-date-part="month">
                                    <option value="">${labels.get('month')}</option>
                                    <c:forEach var="monthNumber" begin="1" end="12">
                                            <option value="${monthNumber}">${monthNumber}</option>
                                    </c:forEach>
                                </select>
                                <select class="jlab-date-field-item"
                                        id="search-form-birth-year-after"
                                        title="${labels.get('year')}"
                                        name="yearOfBirthAfter"
                                        data-date-part="year">
                                    <option value="">${labels.get('year')}</option>
                                    <c:forEach var="i" begin="${yearNow-70}" end="${yearNow}">
                                        <c:set var="year" value="${yearNow - i + yearNow -70}"/>
                                            <option value="${year}">${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-birth-day-before">${labels.get('birth.date.before')}</label>
                            <div class="jlab-date-field"
                                 data-date-block="true">
                                <select class="jlab-date-field-item"
                                        id="search-form-birth-day-before"
                                        title="${labels.get('day')}"
                                        name="dayOfBirthBefore"
                                        data-date-part="day">
                                    <option value="">${labels.get('day')}</option>
                                    <c:forEach var="dayOfMonth" begin="1" end="31">
                                        <option value="${dayOfMonth}">${dayOfMonth}</option>
                                    </c:forEach>
                                </select>
                                <select class="jlab-date-field-item"
                                        id="search-form-birth-month-before"
                                        title="${labels.get('month')}"
                                        name="monthOfBirthBefore"
                                        data-date-part="month">
                                    <option value="">${labels.get('month')}</option>
                                    <c:forEach var="monthNumber" begin="1" end="12">
                                        <option value="${monthNumber}">${monthNumber}</option>
                                    </c:forEach>
                                </select>
                                <select class="jlab-date-field-item"
                                        id="search-form-birth-year-before"
                                        title="${labels.get('year')}"
                                        name="yearOfBirthBefore"
                                        data-date-part="year">
                                    <option value="">${labels.get('year')}</option>
                                    <c:forEach var="i" begin="${yearNow-70}" end="${yearNow}">
                                        <c:set var="year" value="${yearNow - i + yearNow -70}"/>
                                        <option value="${year}">${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="jlab-form-item">
                            <label for="edit-form-sex">${labels.get('sex')}</label>
                            <select id="edit-form-sex"
                                    name="sex">
                                <option value="" selected>${labels.get('sex.any')}</option>
                                <c:forEach items="${sexList}" var="sexType">
                                    <option value="${sexType}">${labels.get(sexType)}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-nationality">${labels.get('nationality')}</label>
                            <input id="search-form-nationality"
                                   type="text"
                                   name="nationality"
                                   placeholder="${labels.get('nationality.placeholder')}"
                                   maxlength="30"
                                   data-validation-type="string30Chars">
                        </div>

                    </div>
                    <div class="jlab-cell-6">
                        <div class="jlab-form-item">
                            <label for="edit-form-marital-status">${labels.get('marital.status')}</label>
                            <select id="edit-form-marital-status"
                                    name="maritalStatus">
                                <option value="" selected>${labels.get('marital.status.any')}</option>
                                <c:forEach items="${maritalStatusList}" var="maritalStatusType">
                                    <option value="${maritalStatusType}">${labels.get(maritalStatusType)}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-country">${labels.get('country')}</label>
                            <input id="search-form-country"
                                   type="text"
                                   name="country"
                                   value=""
                                   placeholder="${labels.get('country.placeholder')}"
                                   maxlength="30"
                                   data-validation-type="string30Chars">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-town">${labels.get('town')}</label>
                            <input id="search-form-town"
                                   type="text"
                                   name="town"
                                   value=""
                                   placeholder="${labels.get('town.placeholder')}"
                                   maxlength="30"
                                   data-validation-type="string30Chars">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-zip-code">${labels.get('zip')}</label>
                            <input id="search-form-zip-code"
                                   type="text"
                                   name="zipCode"
                                   value=""
                                   placeholder="${labels.get('zip.placeholder')}"
                                   maxlength="9"
                                   data-validation-type="zip">
                        </div>


                        <div class="jlab-form-item">
                            <label for="search-form-street">${labels.get('street')}</label>
                            <input id="search-form-street"
                                   type="text"
                                   name="street"
                                   value=""
                                   placeholder="${labels.get('street.placeholder')}"
                                   maxlength="30"
                                   data-validation-type="string30Chars">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-house-number">${labels.get('house.number')}</label>
                            <input id="search-form-house-number"
                                   type="text"
                                   name="houseNumber"
                                   value=""
                                   placeholder="${labels.get('house.number.placeholder')}"
                                   maxlength="10"
                                   data-validation-type="numbers">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-flat-number">${labels.get('flat.number')}</label>
                            <input id="search-form-flat-number"
                                   type="text"
                                   name="flatNumber"
                                   value=""
                                   placeholder="${labels.get('flat.number.placeholder')}"
                                   maxlength="10"
                                   data-validation-type="numbers">
                        </div>

                    </div>
                </div>
                <div class="jlab-row">
                    <div class="jlab-cell-12">
                        <button type="submit"
                                class="jlab-button jlab-pull-right">${labels.get('search')}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="${rootContext}resources/js/contact-search-form.js"></script>