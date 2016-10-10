<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="modal-upload-attachment"
     class="jlab-modal-container jlab-fade">

    <form id="upload-attachment-form"
          class="jlab-modal jlab-phone-number-edit-form">
        <h4>${labels.get('caption.add.attachment')}</h4>
        <div class="jlab-form-item">
            <label id="label-attachment-file-input"
                   class="jlab-button"
                   for="label-attachment-upload">${labels.get('select.file')}</label>
            <label id="label-attachment-upload">${labels.get('no.file.selected')}</label>
        </div>
        <div class="jlab-form-item">
            <label for="attachment-file-name">${labels.get('set.file.name')}</label>
            <input id="attachment-file-name"
                   type="text"
                   value=""
                   name="attachedFileName"
                   maxlength="60"
                   placeholder="${labels.get('set.file.name.placeholder')}">
        </div>
        <div class="jlab-form-item">
            <label for="attachment-file-comment">${labels.get('set.file.comment')}</label>
            <textarea id="attachment-file-comment"
                      name="attachedFileComment"
                      class="jlab-comment-textarea"
                      maxlength="1000"
                      placeholder="${labels.get('set.file.comment.placeholder')}"></textarea>
        </div>
        <div class="jlab-pull-right jlab-button-block ">

            <button id="button-upload-attachment"
                    type="button"
                    class="jlab-button">${labels.get('save')}</button>
            <button id="button-cancel-upload-attachment"
                    type="button"
                    class="jlab-button">${labels.get('cancel')}</button>
        </div>
    </form>
</div>