<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-row">
        <div class="jlab-cell-12">
            <div class="jlab-main-photo-container">

                <c:choose>
                    <c:when test="${(fullContact.photoLink == null) || (fullContact.photoLink eq 'null')}">
                        <img src="../resources/img/unknown_person.png">
                    </c:when>
                   <c:otherwise>
                       <img src="${fullContact.photoLink}">
                   </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
