<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="page_components/header.jsp"/>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-contact-table-caption">
            <div class="jlab-row">
                <div class="jlab-cell-1 jlab-padding-10">

                </div>
                <div class="jlab-cell-3 jlab-padding-10">
                    NAME
                </div>
                <div class="jlab-cell-2 jlab-padding-10">
                    BIRTH DATE
                </div>
                <div class="jlab-cell-4 jlab-padding-10">
                    ADDRESS
                </div>
                <div class="jlab-cell-2 jlab-padding-10">
                    COMPANY
                </div>
            </div>
        </div>
        <ul>
            <c:forEach items="${contacts}" var="contact">
                <li class="jlab-clickable-contact">
                    <div class="jlab-row " onclick="javascript: alert('clickable')">
                        <div class="jlab-cell-1">
                            <div class="jlab-photo-thumbnail-container">

                            </div>
                        </div>
                        <div class="jlab-cell-3">
                            <div class="jlab-contact-full-name jlab-padding-10">
                                    ${contact.fullName}
                            </div>
                        </div>
                        <div class="jlab-cell-2">
                            <div class="jlab-padding-10">
                                    ${contact.dateOfBirth}
                            </div>
                        </div>
                        <div class="jlab-cell-4">
                            <div class="jlab-padding-10">
                                    ${contact.addresses[0]}
                            </div>
                        </div>
                        <div class="jlab-cell-2">
                            <div class="jlab-padding-10">
                                    ${contact.company}
                            </div>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<jsp:include page="page_components/footer.jsp"/>
