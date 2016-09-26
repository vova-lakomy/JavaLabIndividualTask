<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="attachment-table-caption" class="jlab-row">
    <div class="jlab-cell-12 jlab-inner-table-caption">
        <span>Attachments:</span>
        <div class="jlab-pull-right jlab-button-block">
            <button id="button-show-attachments-upload-modal" type="button" class="jlab-button">add new</button>
            <button id="button-delete-attachment" type="button" class="jlab-button">delete</button>
        </div>
    </div>
</div>
<c:choose>
    <c:when test="${attachments != null}">
        <div id="inner-attachment-table" class="jlab-inner-table-container">
            <ul class="jlab-row jlab-inner-table-column-caption">
                <li class="jlab-cell-4">File Name</li>
                <li class="jlab-cell-2">Upload Date</li>
                <li class="jlab-cell-6">Comment</li>
            </ul>
            <div id="attachment-rows">
                <c:forEach var="attachment" items="${attachments}" varStatus="counter">
                    <ul class="jlab-row">
                        <li class="jlab-cell-4">
                            <input class="jlab-hidden" type="text" name="attachmentId-${counter.index}"
                                   value="${attachment.id}">
                            <input class="jlab-hidden" type="text" name="attachmentLink-${counter.index}"
                                   value="${attachment.attachmentLink}">
                            <input class="jlab-hidden" type="text" name="attachmentFileName-${counter.index}"
                                   value="${attachment.fileName}">
                            <input class="jlab-hidden" type="text" name="attachmentComment-${counter.index}"
                                   value="${attachment.comment}">
                            <input class="jlab-hidden" type="text" name="uploadDate-${counter.index}"
                                   value="${attachment.uploadDate}">

                            <input type="checkbox" id="attachment-${counter.index}" name="selectedId"
                                   value="${attachment.id}" data-action="deleteAttachment">
                            <a href="${attachment.attachmentLink}" target="_blank"><label>${attachment.fileName}</label></a>&nbsp;&nbsp;
                            <img class="jlab-edit-image" src="../resources/img/pencil_12x12.png" title="edit" data-action="edit">

                        </li>

                        <li class="jlab-cell-2">
                                ${attachment.uploadDate}
                        </li>

                        <li class="jlab-cell-6">
                                ${attachment.comment}
                        </li>
                    </ul>
                </c:forEach>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="attachments-empty-table" class="jlab-row jlab-vertical-padding-10">
            <div class="jlab-cell-12">
                <i>nothing to display <a
                        href="javascript: toggleClass($('#modal-upload-attachment'),'jlab-fade')">add</a></i> <%// TODO: 21.09.16 hang listener in js %>
            </div>
        </div>
        <div id="inner-attachment-table" class="jlab-inner-table-container jlab-hidden">
            <ul class="jlab-row jlab-inner-table-column-caption">
                <li class="jlab-cell-4">File Name</li>
                <li class="jlab-cell-2">Upload Date</li>
                <li class="jlab-cell-6">Comment</li>
            </ul>
            <div id="attachment-rows"></div>
        </div>
    </c:otherwise>
</c:choose>