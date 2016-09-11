<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="jlab-row jlab-vertical-padding-10">
    <div class="jlab-cell-12">
        <b>Phone numbers:</b>
    </div>
    <div class="jlab-cell-12">
        <div class="jlab-pull-right jlab-button-block">
            <button type="button" class="jlab-button">add new</button>
            <button type="button" class="jlab-button">edit</button>
            <button type="button" class="jlab-button">delete</button>
        </div>
    </div>
</div>

<c:choose>
    <c:when test="${phoneNumbers != null}">

        <div class="jlab-phone-number-table-container">
            <div class="jlab-row jlab-phone-numbers-table-caption jlab-left-padding-15">
                <div class="jlab-cell-2">
                    Phone number
                </div>
                <div class="jlab-cell-2">
                    Type
                </div>
                <div class="jlab-cell-8">
                    Comment
                </div>
            </div>
            <c:forEach var="phoneNumber" items="${phoneNumbers}">

                <div class="jlab-row jlab-vertical-padding-10">
                    <jsp:element name="input">
                        <jsp:attribute name="type">checkbox</jsp:attribute>
                        <jsp:attribute name="id">phoneNumberId=${phoneNumber.id}</jsp:attribute>
                    </jsp:element>
                    <div class="jlab-cell-2">
                        <jsp:element name="label">
                            <jsp:attribute name="for">phoneNumberId=${phoneNumber.id}</jsp:attribute>
                            <jsp:body>${phoneNumber.number}</jsp:body>
                        </jsp:element>
                    </div>
                    <div class="jlab-cell-2">
                            ${phoneNumber.type}
                    </div>
                    <div class="jlab-cell-8">
                            ${phoneNumber.comment}
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="jlab-row jlab-vertical-padding-10">
            <div class="jlab-cell-12">
                <i>nothing to display <a href="">add</a></i>
            </div>
        </div>
    </c:otherwise>
</c:choose>

