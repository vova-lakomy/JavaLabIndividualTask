<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="application/javascript" src="${rootContext}resources/js/validator.js"></script>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-form-container">
            <h4>${labels.get('caption.advanced.search')}</h4>
            <form id="contact-search-form" class="jlab-contact-search-form" method="get" data-form-name="search-form">
                <div class="jlab-row">
                    <div class="jlab-cell-6">
                        <div class="jlab-form-item">
                            <label for="search-form-last-name">${labels.get('last.name')}</label>
                            <input id="search-form-last-name" type="text" name="lastName" value=""
                                   placeholder="${labels.get('last.name.placeholder')}" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-first-name">${labels.get('first.name')}</label>
                            <input id="search-form-first-name" type="text" name="firstName" value=""
                                   placeholder="${labels.get('first.name.placeholder')}" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-second-name">${labels.get('second.name')}</label>
                            <input id="search-form-second-name" type="text" name="secondtName" value=""
                                   placeholder="${labels.get('second.name.placeholder')}" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-birth-day-after">${labels.get('birth.date.after')}</label>
                            <div class="jlab-date-field" data-date-block="true">
                                <input id="search-form-birth-day-after" type="text" placeholder="${labels.get('day')}" title="${labels.get('day')}"
                                       name="dayOfBirthAfter" value="" data-date-part="day">
                                <input id="search-form-birth-month-after" type="text" placeholder="${labels.get('month')}" title="${labels.get('month')}"
                                       name="monthOfBirthAfter" value="" data-date-part="month">
                                <input id="search-form-birth-year-after" type="text" placeholder="${labels.get('year')}" title="${labels.get('year')}"
                                       name="yearOfBirthAfter" value="" data-date-part="year">
                            </div>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-birth-day-before">${labels.get('birth.date.before')}</label>
                            <div class="jlab-date-field" data-date-block="true">
                                <input id="search-form-birth-day-before" type="text" placeholder="${labels.get('day')}" title="${labels.get('day')}"
                                       name="dayOfBirthBefore" value="" data-date-part="day">
                                <input id="search-form-birth-month-before" type="text" placeholder="${labels.get('month')}" title="${labels.get('month')}"
                                       name="monthOfBirthBefore" value="" data-date-part="month">
                                <input id="search-form-birth-year-before" type="text" placeholder="${labels.get('year')}" title="${labels.get('year')}"
                                       name="yearOfBirthBefore" value="" data-date-part="year">
                            </div>
                        </div>

                        <div class="jlab-form-item">
                            <label for="edit-form-sex">${labels.get('sex')}</label>
                            <select id="edit-form-sex" name="sex">
                                <option value="" selected>${labels.get('sex.any')}</option>
                                <c:forEach items="${sexList}" var="sexType">
                                    <option value="${sexType}">${labels.get(sexType)}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-nationality">${labels.get('nationality')}</label>
                            <input id="search-form-nationality" type="text" name="nationality"
                                   placeholder="${labels.get('nationality.placeholder')}" data-validation-type="string">
                        </div>

                    </div>
                    <div class="jlab-cell-6">
                        <div class="jlab-form-item">
                            <label for="edit-form-martial-status">${labels.get('martial.status')}</label>
                            <select id="edit-form-martial-status" name="martialStatus">
                                <option value="" selected>${labels.get('martial.status.any')}</option>
                                <c:forEach items="${martialStatusList}" var="martialStatusType">
                                    <option value="${martialStatusType}">${labels.get(martialStatusType)}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-country">${labels.get('country')}</label>
                            <input id="search-form-country" type="text" name="country" value=""
                                   placeholder="${labels.get('country.placeholder')}" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-town">${labels.get('town')}</label>
                            <input id="search-form-town" type="text" name="town" value=""
                                   placeholder="${labels.get('town.placeholder')}" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-zip-code">${labels.get('zip')}</label>
                            <input id="search-form-zip-code" type="text" name="zipCode" value=""
                                   placeholder="${labels.get('zip.placeholder')}" data-validation-type="zip">
                        </div>


                        <div class="jlab-form-item">
                            <label for="search-form-street">${labels.get('street')}</label>
                            <input id="search-form-street" type="text" name="street" value=""
                                   placeholder="${labels.get('street.placeholder')}" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-house-number">${labels.get('house.number')}</label>
                            <input id="search-form-house-number" type="text" name="houseNumber" value=""
                                   placeholder="${labels.get('house.number.placeholder')}" data-validation-type="numbers">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-flat-number">${labels.get('flat.number')}</label>
                            <input id="search-form-flat-number" type="text" name="flatNumber" value=""
                                   placeholder="${labels.get('flat.number.placeholder')}" data-validation-type="numbers">
                        </div>

                    </div>
                </div>
                <div class="jlab-row">
                    <div class="jlab-cell-12">
                        <button type="submit" class="jlab-button jlab-pull-right">${labels.get('search')}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="${rootContext}resources/js/contact-search-form.js"></script>