<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="modal-upload-photo" class="jlab-modal-container jlab-fade">
    <div id="upload-form" class="jlab-modal jlab-phone-number-edit-form">
        <h4>${labels.get('download.or.upload.image')}</h4>
        <ul>

            <li class="jlab-row">
                <div class="jlab-cell-12">
                    <c:choose>
                        <c:when test="${fullContact.photoLink eq null}">
                            <button class="jlab-button" disabled>${labels.get('download.image')}</button>
                        </c:when>
                        <c:otherwise>
                            <a href="${rootContext}contacts/${fullContact.photoLink}" class="jlab-button" download>${labels.get('download.image')}</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </li>


            <li class="jlab-row">
                <div class="jlab-cell-12">
                    <label class="jlab-button" for="photo-file-input">${labels.get('upload.image')}</label>
                    <input class="jlab-hidden" id="photo-file-input" type="file" name="attachedPhoto" accept="image/*">
                </div>

            </li>
            <li class="jlab-row">
                <div class="jlab-cell-12">
                    <label id="input-file-name">${labels.get('no.image.selected')}</label>
                </div>
            </li>

        </ul>


        <div class="jlab-pull-right jlab-button-block">
            <button id="button-upload-photo" type="button" class="jlab-button">${labels.get('ok')}</button>
            <button id="button-cancel-upload-photo" type="button" class="jlab-button">${labels.get('cancel')}</button>
        </div>


    </div>
</div>