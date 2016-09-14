<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="attachment-table-caption" class="jlab-row">
    <div class="jlab-cell-12 jlab-inner-table-caption">
        <span>Attachments:</span>
        <div class="jlab-pull-right jlab-button-block">
            <button type="button" class="jlab-button">add new</button>
            <button type="button" class="jlab-button"
                    onclick="toggleClass($('#modal-attachment-edit'),'jlab-fade')">edit</button>
            <button type="button" class="jlab-button">delete</button>
        </div>
    </div>
</div>
<c:choose>
    <c:when test="${attachments != null}">

        <div class="jlab-inner-table-container">
            <div class="jlab-row jlab-inner-table-column-caption jlab-left-padding-15">
                <div class="jlab-cell-2">
                    File Name
                </div>
                <div class="jlab-cell-2">
                    Upload Date
                </div>
                <div class="jlab-cell-8">
                    Comment
                </div>
            </div>
            <c:forEach var="attachment" items="${attachments}">

                <div class="jlab-row jlab-vertical-padding-10">
                    <input class="jlab-hidden" type="text" name="attachmentId" value="${attachment.id}">
                    <input type="checkbox" id="attachment-id ${attachment.id}">
                    <div class="jlab-cell-2">
                        <label for="attachment-id ${attachment.id}">${attachment.fileName}</label>
                    </div>
                    <div class="jlab-cell-2">
                            ${attachment.uploadDate}
                    </div>
                    <div class="jlab-cell-8">
                            ${attachment.comment}
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="jlab-row jlab-vertical-padding-10">
            <div class="jlab-cell-12">
                <i>nothing to display <a href="javascript: toggleClass($('#modal-attachment-edit'),'jlab-fade')">add</a></i>
            </div>
        </div>
    </c:otherwise>
</c:choose>
