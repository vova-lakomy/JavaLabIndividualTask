<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="modal-attachment-edit"
     class="jlab-modal-container jlab-fade">
    <form id="modal-attachment-edit-form"
          class="jlab-modal jlab-phone-number-edit-form">
        <h4>${labels.get('caption.edit.attachment')}</h4>
        <div class="jlab-form-item">
            <input class="jlab-hidden"
                   id="attachment-file-input"
                   type="file"
                   value=""
                   name="attachedFile">
            <input class="jlab-hidden"
                   type="text"
                   name="attachmentId"
                   value="">
            <input class="jlab-hidden"
                   type="text"
                   name="attachmentLink"
                   value="">
            <input class="jlab-hidden"
                   type="text"
                   name="attachmentCounter"
                   value="">
            <input class="jlab-hidden"
                   type="text"
                   name="uploadDate"
                   value="">
            <input class="jlab-hidden"
                   type="text"
                   name="attachmentOldName"
                   value="">
            <label for="attachment-name">${labels.get('attachment.name')}</label>
            <input id="attachment-name"
                   name="attachmentName"
                   type="text"
                   value=""
                   maxlength="60"
                   placeholder="${labels.get('attachment.name.placeholder')}">
        </div>

        <div class="jlab-form-item">
            <label for="attachment-comment">${labels.get('comment')}</label>
            <textarea id="attachment-comment"
                      class="jlab-comment-textarea"
                      name="attachmentComment"
                      maxlength="1000"
                      placeholder="${labels.get('comment.placeholder')}"></textarea>
        </div>
        <div class="jlab-pull-right jlab-button-block ">
            <button id="button-save-attachment-edit-modal"
                    type="button"
                    class="jlab-button">${labels.get('save')}</button>
            <button id="button-cancel-attachments-edit-modal"
                    type="button"
                    class="jlab-button">${labels.get('cancel')}</button>
        </div>
    </form>
</div>

