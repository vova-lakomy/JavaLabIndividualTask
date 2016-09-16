<div id="modal-upload-photo" class="jlab-modal-container jlab-fade">
    <div id="upload-form" class="jlab-modal jlab-phone-number-edit-form">
        <h4>Ulpoad photo</h4>
        <div class="jlab-form-item">
            <label for="photo-file-input">Select image</label>
            <input id="photo-file-input" class="file" type="file" name="data" accept="image/*">
        </div>

        <div class="jlab-pull-right jlab-button-block ">
            <button id="upload-photo" type="button" class="jlab-button">save</button>
            <button type="button" class="jlab-button"
                    onclick="toggleClass($('#modal-upload-photo'),'jlab-fade')">cancel</button>
        </div>
    </div>
</div>