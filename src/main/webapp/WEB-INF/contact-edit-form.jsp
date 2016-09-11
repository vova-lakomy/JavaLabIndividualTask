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

<jsp:include page="page_components/contact-edit-form/modal-phone-edit.jsp"/>

