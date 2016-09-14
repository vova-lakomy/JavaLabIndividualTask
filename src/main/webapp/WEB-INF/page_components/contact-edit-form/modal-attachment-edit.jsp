<div id="modal-attachment-edit" class="jlab-modal-container jlab-fade">
    <form class="jlab-modal jlab-phone-number-edit-form">
        <h4>Edit attachment</h4>
        <div class="jlab-form-item">
            <label for="attachment-name">Attachment name</label>
            <input id="attachment-name" name="attachmentName" type="text" value="" placeholder="attachment name">
        </div>

        <div class="jlab-form-item">
            <label for="attachment-comment">Comment</label>
            <input id="attachment-comment" name="attachmentComment" type="text" value="" placeholder="comment">
        </div>
        <div class="jlab-form-item">
            <button type="button" class="jlab-button">save</button>
            <button type="button" class="jlab-button"
                    onclick="toggleClass($('#modal-attachment-edit'),'jlab-fade')">cancel</button>
        </div>
    </form>
</div>

