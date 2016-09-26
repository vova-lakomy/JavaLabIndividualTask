<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="modal-upload-attachment" class="jlab-modal-container jlab-fade">

    <form id="upload-attachment-form" class="jlab-modal jlab-phone-number-edit-form">
        <h4>Add attachment</h4>
            <div class="jlab-form-item">
                <label for="attachment-file-input">Select file</label>
                <input id="attachment-file-input" type="file" value="" name="attachedFile">
            </div>
            <div class="jlab-form-item">
                <label for="attachment-file-name">Set file name</label>
                <input id="attachment-file-name" type="text" value="" name="attachedFileName">
            </div>
            <div class="jlab-form-item">
                <label for="attachment-file-comment">Set file comment</label>
                <input id="attachment-file-comment" type="text" value="" name="attachedFileComment">
            </div>

            <div class="jlab-pull-right jlab-button-block ">
                <button id="button-upload-attachment" type="button" class="jlab-button">save</button>
                <button id="button-cancel-upload-attachment" type="button" class="jlab-button">cancel</button>
            </div>
    </form>
</div>