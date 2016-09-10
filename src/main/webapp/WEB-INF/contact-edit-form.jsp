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

                <div class="jlab-row jlab-vertical-padding-10">
                    <div class="jlab-cell-12">
                        Phone numbers:
                    </div>
                    <div class="jlab-cell-12">
                        <div class="jlab-pull-right jlab-button-block">
                            <button type="button" class="jlab-button">add new</button>
                            <button type="button" class="jlab-button">edit</button>
                            <button type="button" class="jlab-button" disabled>delete</button>
                        </div>
                    </div>
                </div>

                <div class="jlab-row jlab-vertical-padding-10">
                    <div class="jlab-cell-12">
                        Attachments:
                    </div>
                </div>

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
