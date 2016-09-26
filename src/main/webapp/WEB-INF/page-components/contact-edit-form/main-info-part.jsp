<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-row">
    <div class="jlab-cell-12">

        <div class="jlab-row">

            <div class="jlab-cell-6">
                <div class="jlab-form-item">
                    <input class="jlab-hidden" name="contactId" value="${fullContact.id}">
                    <label for="edit-form-first-name">First name</label>
                    <input id="edit-form-first-name" type="text" name="firstName" value="${fullContact.firstName}"
                        placeholder="first name" data-validation-type="stringRequired" required/>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-last-name">Last name</label>
                    <input id="edit-form-last-name" type="text" name="lastName" value="${fullContact.lastName}"
                           placeholder="last name" data-validation-type="stringRequired" required>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-second-name">Second name</label>
                    <input id="edit-form-second-name" type="text" name="secondName" value="${fullContact.secondName}"
                           placeholder="second name" data-validation-type="string">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-birth-day">Birth date</label>
                    <div id="date-field" class="jlab-date-field" data-date-block="true">
                        <input id="edit-form-birth-day" type="text" placeholder="day" title="day" name="dayOfBirth"
                               value="${fullContact.dayOfBirth}" data-date-part="day">
                        <input id="edit-form-birth-month" type="text" placeholder="month" title="month" name="monthOfBirth"
                               value="${fullContact.monthOfBirth}" data-date-part="month">
                        <input id="edit-form-birth-year" type="text" placeholder="year" title="year" name="yearOfBirth"
                               value="${fullContact.yearOfBirth}" data-date-part="year">
                    </div>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-sex">Sex</label>
                    <select id="edit-form-sex" name="sex">
                        <c:forEach items="${sexList}" var="sexType">
                            <c:choose>
                                <c:when test="${sexType eq fullContact.sex}">
                                    <option selected value="${sexType}">${sexType}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${sexType}">${sexType}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

            </div>


            <div class="jlab-cell-6">
                <div class="jlab-form-item">
                    <label for="edit-form-nationality">Nationality</label>
                    <input id="edit-form-nationality" type="text" name="nationality" value="${fullContact.nationality}"
                           placeholder="nationality" data-validation-type="string">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-martial-status">Martial status</label>
                    <select id="edit-form-martial-status" name="martialStatus">
                        <c:forEach items="${martialStatusList}" var="martialStatusType">
                            <c:choose>
                                <c:when test="${martialStatusType eq fullContact.martialStatus}">
                                    <option selected value="${martialStatusType}">${martialStatusType}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${martialStatusType}">${martialStatusType}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-web-site">Web site</label>
                    <input id="edit-form-web-site" type="text" name="webSite" value="${fullContact.webSite}"
                           placeholder="web site" data-validation-type="url">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-email">e-mail</label>
                    <input id="edit-form-email" type="text" name="eMail" value="${fullContact.eMail}"
                           placeholder="e-mail" data-validation-type="email">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-company">Company</label>
                    <input id="edit-form-company" type="text" name="currentJob" value="${fullContact.currentJob}"
                           placeholder="company" data-validation-type="string">
                </div>


            </div>

        </div>
    </div>
</div>
