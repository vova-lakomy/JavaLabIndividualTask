<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-form-container">
            <h4>Advanced search</h4>
            <form id="contact-search-form" class="jlab-contact-search-form" method="get" data-form-name="search-form">
                <div class="jlab-row">
                    <div class="jlab-cell-6">
                        <div class="jlab-form-item">
                            <label for="search-form-last-name">Last name</label>
                            <input id="search-form-last-name" type="text" name="lastName" value=""
                                   placeholder="last name" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-first-name">First name</label>
                            <input id="search-form-first-name" type="text" name="firstName" value=""
                                   placeholder="first name" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-second-name">Second name</label>
                            <input id="search-form-second-name" type="text" name="secondtName" value=""
                                   placeholder="second name" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-birth-day-after">Birth date after</label>
                            <div class="jlab-date-field" data-date-block="true">
                                <input id="search-form-birth-day-after" type="text" placeholder="day" title="day"
                                       name="dayOfBirthAfter" value="" data-date-part="day">
                                <input id="search-form-birth-month-after" type="text" placeholder="month" title="month"
                                       name="monthOfBirthAfter" value="" data-date-part="month">
                                <input id="search-form-birth-year-after" type="text" placeholder="year" title="year"
                                       name="yearOfBirthAfter" value="" data-date-part="year">
                            </div>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-birth-day-before">Birth date before</label>
                            <div class="jlab-date-field" data-date-block="true">
                                <input id="search-form-birth-day-before" type="text" placeholder="day" title="day"
                                       name="dayOfBirthBefore" value="" data-date-part="day">
                                <input id="search-form-birth-month-before" type="text" placeholder="month" title="month"
                                       name="monthOfBirthBefore" value="" data-date-part="month">
                                <input id="search-form-birth-year-before" type="text" placeholder="year" title="year"
                                       name="yearOfBirthBefore" value="" data-date-part="year">
                            </div>
                        </div>

                        <div class="jlab-form-item">
                            <label for="edit-form-sex">Sex</label>
                            <select id="edit-form-sex" name="sex">
                                <option value="" selected>any</option>
                                <c:forEach items="${sexList}" var="sexType">
                                    <option value="${sexType}">${sexType}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-nationality">Nationality</label>
                            <input id="search-form-nationality" type="text" name="nationality"
                                   placeholder="nationality" data-validation-type="string">
                        </div>

                    </div>
                    <div class="jlab-cell-6">
                        <div class="jlab-form-item">
                            <label for="edit-form-martial-status">Martial status</label>
                            <select id="edit-form-martial-status" name="martialStatus">
                                <option value="" selected>any</option>
                                <c:forEach items="${martialStatusList}" var="martialStatusType">
                                    <option value="${martialStatusType}">${martialStatusType}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-country">Country</label>
                            <input id="search-form-country" type="text" name="country" value="" placeholder="country"
                                   data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-town">Town</label>
                            <input id="search-form-town" type="text" name="town" value="" placeholder="town"
                                   data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-zip-code">Zip</label>
                            <input id="search-form-zip-code" type="text" name="zipCode" value=""
                                   placeholder="zipcode" data-validation-type="zip">
                        </div>


                        <div class="jlab-form-item">
                            <label for="search-form-street">Street</label>
                            <input id="search-form-street" type="text" name="street" value=""
                                   placeholder="street" data-validation-type="string">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-house-number">House number</label>
                            <input id="search-form-house-number" type="text" name="houseNumber" value=""
                                   placeholder="house number" data-validation-type="numbers">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-flat-number">Flat number</label>
                            <input id="search-form-flat-number" type="text" name="flatNumber" value=""
                                   placeholder="house number" data-validation-type="numbers">
                        </div>

                    </div>
                </div>
                <div class="jlab-row">
                    <div class="jlab-cell-12">
                        <button type="submit" class="jlab-button jlab-pull-right">search</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="../resources/js/contact-search-form.js"></script>