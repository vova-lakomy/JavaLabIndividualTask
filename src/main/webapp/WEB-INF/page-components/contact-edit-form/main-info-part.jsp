<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-row">
    <div class="jlab-cell-12">

        <div class="jlab-row">

            <div class="jlab-cell-6">
                <div class="jlab-form-item">
                    <input class="jlab-hidden" name="contactId" value="${fullContact.id}">
                    <label for="edit-form-first-name">${labels.get('first.name')}</label>
                    <input id="edit-form-first-name" type="text" name="firstName" value="${fullContact.firstName}"
                        placeholder="${labels.get('first.name.placeholder')}" data-validation-type="stringRequired" required/>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-last-name">${labels.get('last.name')}</label>
                    <input id="edit-form-last-name" type="text" name="lastName" value="${fullContact.lastName}"
                           placeholder="${labels.get('last.name.placeholder')}" data-validation-type="stringRequired" required>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-second-name">${labels.get('second.name')}</label>
                    <input id="edit-form-second-name" type="text" name="secondName" value="${fullContact.secondName}"
                           placeholder="${labels.get('second.name.placeholder')}" data-validation-type="string">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-birth-day">${labels.get('birth.date')}</label>
                    <div id="date-field" class="jlab-date-field" data-date-block="true">
                        <input id="edit-form-birth-day" type="text" placeholder="${labels.get('day')}" title="${labels.get('day')}y" name="dayOfBirth"
                               value="${fullContact.dayOfBirth}" data-date-part="day">
                        <input id="edit-form-birth-month" type="text" placeholder="${labels.get('month')}" title="${labels.get('month')}" name="monthOfBirth"
                               value="${fullContact.monthOfBirth}" data-date-part="month">
                        <input id="edit-form-birth-year" type="text" placeholder="${labels.get('year')}" title="${labels.get('year')}" name="yearOfBirth"
                               value="${fullContact.yearOfBirth}" data-date-part="year">
                    </div>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-sex">${labels.get('sex')}</label>
                    <select id="edit-form-sex" name="sex">
                        <option value=""> </option>
                        <c:forEach items="${sexList}" var="sexType">
                            <c:choose>
                                <c:when test="${sexType eq fullContact.sex}">
                                    <option selected value="${sexType}">${labels.get(sexType)}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${sexType}">${labels.get(sexType)}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

            </div>


            <div class="jlab-cell-6">
                <div class="jlab-form-item">
                    <label for="edit-form-nationality">${labels.get('nationality')}</label>
                    <input id="edit-form-nationality" type="text" name="nationality" value="${fullContact.nationality}"
                           placeholder="${labels.get('nationality.placeholder')}" data-validation-type="string">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-martial-status">${labels.get('martial.status')}</label>
                    <select id="edit-form-martial-status" name="martialStatus">
                        <option value=""> </option>
                        <c:forEach items="${martialStatusList}" var="martialStatusType">
                            <c:choose>
                                <c:when test="${martialStatusType eq fullContact.martialStatus}">
                                    <option selected value="${martialStatusType}">${labels.get(martialStatusType)}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${martialStatusType}">${labels.get(martialStatusType)}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-web-site">${labels.get('web.site')}</label>
                    <input id="edit-form-web-site" type="text" name="webSite" value="${fullContact.webSite}"
                           placeholder="${labels.get('web.site.placeholder')}" data-validation-type="url">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-email">${labels.get('e.mail')}</label>
                    <input id="edit-form-email" type="text" name="eMail" value="${fullContact.eMail}"
                           placeholder="${labels.get('e.mail.placeholder')}" data-validation-type="email">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-company">${labels.get('company')}</label>
                    <input id="edit-form-company" type="text" name="currentJob" value="${fullContact.currentJob}"
                           placeholder="${labels.get('company.placeholder')}" data-validation-type="string">
                </div>


            </div>

        </div>
    </div>
</div>
