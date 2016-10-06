<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date"/>
<c:set var="yearNow" value="${now.year +1900}"/>
<div class="jlab-row">
    <div class="jlab-cell-12">

        <div class="jlab-row">

            <div class="jlab-cell-6">
                <div class="jlab-form-item">
                    <input class="jlab-hidden" name="contactId" value="${fullContact.id}">
                    <label for="edit-form-first-name">${labels.get('first.name')}<span class="jlab-required">*</span></label>
                    <input id="edit-form-first-name" type="text" name="firstName" value="${fullContact.firstName}"
                        placeholder="${labels.get('first.name.placeholder')}" data-validation-type="stringRequired20Chars" required/>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-last-name">${labels.get('last.name')}<span class="jlab-required">*</span></label>
                    <input id="edit-form-last-name" type="text" name="lastName" value="${fullContact.lastName}"
                           placeholder="${labels.get('last.name.placeholder')}" data-validation-type="stringRequired20Chars" required>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-second-name">${labels.get('second.name')}</label>
                    <input id="edit-form-second-name" type="text" name="secondName" value="${fullContact.secondName}"
                           placeholder="${labels.get('second.name.placeholder')}" data-validation-type="string20Chars">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-birth-day">${labels.get('birth.date')}</label>
                    <div id="date-field" class="jlab-date-field" data-date-block="true">
                        <select class="jlab-date-field-item" id="edit-form-birth-day" title="${labels.get('day')}" name="dayOfBirth" data-date-part="day">
                            <option value="">${labels.get('day')}</option>
                          <c:forEach var="dayOfMonth" begin="1" end="31">
                              <c:choose>
                                  <c:when test="${dayOfMonth == fullContact.dayOfBirth}">
                                      <option selected value="${dayOfMonth}">${dayOfMonth}</option>
                                  </c:when>
                                  <c:otherwise>
                                      <option value="${dayOfMonth}">${dayOfMonth}</option>
                                  </c:otherwise>
                              </c:choose>
                          </c:forEach>
                        </select>
                        <select class="jlab-date-field-item" id="edit-form-birth-month" title="${labels.get('month')}" name="monthOfBirth" data-date-part="month">
                            <option value="">${labels.get('month')}</option>
                            <c:forEach var="monthNumber" begin="1" end="12">
                                <c:choose>
                                    <c:when test="${monthNumber == fullContact.monthOfBirth}">
                                        <option selected value="${monthNumber}">${monthNumber}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${monthNumber}">${monthNumber}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <select class="jlab-date-field-item" id="edit-form-birth-year" title="${labels.get('year')}" name="yearOfBirth" data-date-part="year">
                            <option value="">${labels.get('year')}</option>
                            <c:forEach var="i" begin="${yearNow-70}" end="${yearNow}">
                                <c:set var="year" value="${yearNow - i + yearNow -70}"/>
                                <c:choose>
                                    <c:when test="${year == fullContact.yearOfBirth}">
                                        <option selected value="${year}">${year}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${year}">${year}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
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
                           placeholder="${labels.get('nationality.placeholder')}" data-validation-type="string20Chars">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-marital-status">${labels.get('marital.status')}</label>
                    <select id="edit-form-marital-status" name="maritalStatus">
                        <option value=""> </option>
                        <c:forEach items="${maritalStatusList}" var="maritalStatusType">
                            <c:choose>
                                <c:when test="${maritalStatusType eq fullContact.maritalStatus}">
                                    <option selected value="${maritalStatusType}">${labels.get(maritalStatusType)}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${maritalStatusType}">${labels.get(maritalStatusType)}</option>
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
                           placeholder="${labels.get('company.placeholder')}" data-validation-type="string30Chars">
                </div>


            </div>

        </div>
    </div>
</div>
