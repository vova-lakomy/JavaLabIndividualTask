<div id="modal-upload-photo" class="jlab-modal-container jlab-fade">
    <form id="upload-form" class="jlab-modal jlab-phone-number-edit-form" enctype="multipart/form-data">
        <h4>Ulpoad photo</h4>
        <div class="jlab-form-item">
            <label for="file-input">Select image</label>
            <input id="file-input" class="file" type="file" name="data" accept="image/*">
        </div>

        <div class="jlab-pull-right jlab-button-block ">
            <button id="upload-photo" type="button" class="jlab-button">save</button>
            <button type="button" class="jlab-button"
                    onclick="toggleClass($('#modal-upload-photo'),'jlab-fade')">cancel</button>
        </div>
    </form>
</div>