<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-edit-contact-form-container">
            <div class="jlab-row">
                <div class="jlab-cell-12">
                    <div class="jlab-main-photo-container">

                    </div>
                </div>
            </div>

            <form action="javascript: alert('hello')">

                <jsp:include page="page_components/contact-edit-form/main-info-part.jsp"/>

                <jsp:include page="page_components/contact-edit-form/address-part.jsp"/>

                <jsp:include page="page_components/contact-edit-form/phone-numbers-part.jsp"/>

                <jsp:include page="page_components/contact-edit-form/attachments-part.jsp"/>

                <%--submit--%>
                <div class="jlab-row jlab-vertical-padding-10">
                    <div class="jlab-cell-12">
                        <button type="submit" class="jlab-button">submit</button>
                    </div>
                </div>
                <%--/submit--%>

            </form>
        </div>
    </div>
</div>
<form class="jlab-modal jlab-phone-number-edit-form">
    <div class="jlab-form-item">
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
            <option>mobile</option>
            <option>home</option>
        </select>
    </div>

    <div class="jlab-form-item">
        <label for="phone-number-comment">Comment</label>
        <input id="phone-number-comment" type="text" value="" placeholder="comment">
    </div>
</form>
