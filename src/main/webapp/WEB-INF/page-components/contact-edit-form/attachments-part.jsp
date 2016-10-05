<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="attachment-table-caption" class="jlab-row">
    <div class="jlab-cell-12 jlab-inner-table-caption">
        <span>${labels.get('inner.caption.attachments')}:</span>
        <div class="jlab-pull-right jlab-button-block">
            <button id="button-show-attachments-upload-modal" type="button"
                    class="jlab-button">${labels.get('add.new')}</button>
            <button id="button-delete-attachment" type="button" class="jlab-button">${labels.get('delete')}</button>
        </div>
    </div>
</div>
<c:choose>
    <c:when test="${fullContact.attachments != null && fullContact.attachments.size() > 0}">
        <div id="inner-attachment-table" class="jlab-inner-table-container">
            <ul class="jlab-row jlab-inner-table-column-caption">
                <li class="jlab-cell-4">${labels.get('inner.caption.file.name')}</li>
                <li class="jlab-cell-2">${labels.get('inner.caption.upload.date')}</li>
                <li class="jlab-cell-6">${labels.get('comment')}</li>
            </ul>
            <div id="attachment-rows">
                <c:forEach var="attachment" items="${fullContact.attachments}" varStatus="counter">
                    <ul class="jlab-row">
                        <li class="jlab-cell-4 jlab-attachment-file-name">
                            <input class="jlab-hidden" type="text" name="attachmentId-${counter.index}"
                                   value="${attachment.id}">
                            <input class="jlab-hidden" type="text" name="attachmentLink-${counter.index}"
                                   value="${attachment.attachmentLink}">
                            <input class="jlab-hidden" type="text" name="attachmentFileName-${counter.index}"
                                   value="${attachment.fileName}">
                            <textarea class="jlab-hidden" name="attachmentComment-${counter.index}">${attachment.comment}</textarea>
                            <input class="jlab-hidden" type="text" name="uploadDate-${counter.index}"
                                   value="${attachment.uploadDate}">

                            <input type="checkbox" id="attachment-${counter.index}" name="selectedId"
                                   value="${attachment.id}" data-action="deleteAttachment">
                            <a href="${rootContext}${attachment.attachmentLink}" target="_blank" download>
                                <label>${attachment.fileName}</label>
                            </a>&nbsp;&nbsp;
                            <img class="jlab-edit-image"
                                 src="${rootContext}resources/img/pencil_12x12.png" title="edit" data-action="edit">
                        </li>

                        <li class="jlab-cell-2 jlab-attachment-upload-date"> ${attachment.uploadDate}</li>

                        <li class="jlab-cell-6">
                         <textarea readonly class="jlab-textarea-read-only">${attachment.comment}</textarea> </li>
                    </ul>
                </c:forEach>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="attachments-empty-table" class="jlab-row jlab-vertical-padding-10">
            <div class="jlab-cell-12">
                <i>${labels.get('nothing.to.display')}
                    <a id="hrefAddAttachment" href=""> ${labels.get('add')} </a>
                </i>
            </div>
        </div>
        <div id="inner-attachment-table" class="jlab-inner-table-container jlab-hidden">
            <ul class="jlab-row jlab-inner-table-column-caption">
                <li class="jlab-cell-4">${labels.get('inner.caption.file.name')}</li>
                <li class="jlab-cell-2">${labels.get('inner.caption.upload.date')}</li>
                <li class="jlab-cell-6">${labels.get('comment')}</li>
            </ul>
            <div id="attachment-rows"></div>
        </div>
    </c:otherwise>
</c:choose>
