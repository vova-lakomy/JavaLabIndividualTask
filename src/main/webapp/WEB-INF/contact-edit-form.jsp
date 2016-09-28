<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="application/javascript" src="${rootContext}/resources/js/validator.js"></script>
<c:set var="fullContact" value="${fullContactInfo}" scope="request"/>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-form-container">

            <form id="contact-edit-form" action="save" accept-charset="UTF-8" method="post"
                                                       enctype="multipart/form-data" data-form-name="contact-edit-form">
                <div class="jlab-row">
                    <div class="jlab-cell-3">
                        <jsp:include page="page-components/contact-edit-form/contact-photo-part.jsp"/>
                    </div>
                    <div class="jlab-cell-9">
                        <jsp:include page="page-components/contact-edit-form/main-info-part.jsp"/>
                    </div>
                </div>

                <jsp:include page="page-components/contact-edit-form/address-part.jsp"/>

                <jsp:include page="page-components/contact-edit-form/phone-numbers-part.jsp"/>

                <jsp:include page="page-components/contact-edit-form/attachments-part.jsp"/>

                <div class="jlab-row jlab-vertical-padding-10">
                    <div class="jlab-cell-12">
                        <button type="submit" class="jlab-button jlab-pull-right">${labels.get('submit')}</button>
                    </div>
                </div>

            </form>

        </div>
    </div>
</div>

<jsp:include page="page-components/contact-edit-form/modal-phone-edit.jsp"/>
<jsp:include page="page-components/contact-edit-form/modal-phone-add.jsp"/>
<jsp:include page="page-components/contact-edit-form/modal-attachment-edit.jsp"/>
<jsp:include page="page-components/contact-edit-form/modal-attachment-upload.jsp"/>
<script type="text/javascript" src="${rootContext}/resources/js/contact-edit-form.js"></script>



