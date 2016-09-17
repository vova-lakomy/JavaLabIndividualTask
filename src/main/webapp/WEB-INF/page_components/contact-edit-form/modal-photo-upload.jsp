<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="modal-upload-photo" class="jlab-modal-container jlab-fade">
    <div id="upload-form" class="jlab-modal jlab-phone-number-edit-form">
        <h4>Ulpoad photo</h4>
        <div class="jlab-form-item">
            <label for="photo-file-input">Select image</label>
            <input id="photo-file-input" class="file" type="file" name="attachedPhoto" accept="image/*">
        </div>

        <div class="jlab-pull-right jlab-button-block ">
            <button id="button-upload-photo" type="button" class="jlab-button">save</button>
            <button id="button-cancel-upload-photo" type="button" class="jlab-button">cancel</button>
        </div>
    </div>
</div>