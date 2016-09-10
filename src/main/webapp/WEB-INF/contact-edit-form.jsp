<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:include page="page_components/header.jsp"/>--%>

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
                        <div class="jlab-pull-right">
                            <button class="jlab-button-left">add new</button>
                            <button class="jlab-button-middle">edit</button>
                            <button class="jlab-button-right">delete</button>
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


<%--<jsp:include page="page_components/footer.jsp"/>--%>