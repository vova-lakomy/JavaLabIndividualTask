<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-form-container">
            <h4>Advanced search</h4>
            <form class="jlab-contact-search-form">
                <div class="jlab-row">
                    <div class="jlab-cell-6">
                        <div class="jlab-form-item">
                            <label for="search-form-first-name">First name</label>
                            <input id="search-form-first-name" type="text" name="firstName" value=""
                                   placeholder="first name">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-last-name">Last name</label>
                            <input id="search-form-last-name" type="text" name="lastName" value=""
                                   placeholder="last name">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-second-name">Second name</label>
                            <input id="search-form-second-name" type="text" name="secondtName" value=""
                                   placeholder="second name">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-birth-day-from">Birth date from</label>
                            <div class="jlab-date-field">
                                <input id="search-form-birth-day-from" type="text" placeholder="day" title="day"
                                       name="dayOfBirth" value="">
                                <input id="search-form-birth-month-from" type="text" placeholder="month" title="month"
                                       name="monthOfBirth" value="">
                                <input id="search-form-birth-year-from" type="text" placeholder="year" title="year"
                                       name="yearOfBirth" value="">
                            </div>
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-birth-day-till">Birth date till</label>
                            <div class="jlab-date-field">
                                <input id="search-form-birth-day-till" type="text" placeholder="day" title="day"
                                       name="dayOfBirth" value="">
                                <input id="search-form-birth-month-till" type="text" placeholder="month" title="month"
                                       name="monthOfBirth" value="">
                                <input id="search-form-birth-year-till" type="text" placeholder="year" title="year"
                                       name="yearOfBirth" value="">
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
                                   placeholder="nationality">
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
                            <input id="search-form-country" type="text" name="country" value="" placeholder="country">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-town">Town</label>
                            <input id="search-form-town" type="text" name="town" value="" placeholder="town">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-zip-code">Zip</label>
                            <input id="search-form-zip-code" type="text" name="zipCode" value=""
                                   placeholder="zipcode">
                        </div>


                        <div class="jlab-form-item">
                            <label for="search-form-street">Street</label>
                            <input id="search-form-street" type="text" name="street" value=""
                                   placeholder="street">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-house-number">House number</label>
                            <input id="search-form-house-number" type="text" name="houseNumber" value=""
                                   placeholder="house number">
                        </div>

                        <div class="jlab-form-item">
                            <label for="search-form-flat-number">Flat number</label>
                            <input id="search-form-flat-number" type="text" name="flatNumber" value=""
                                   placeholder="house number">
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