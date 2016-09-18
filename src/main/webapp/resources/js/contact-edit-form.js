(function () {
    function getPhoneNumberData() {
        var form = $('#phone-edit-form');
        return {
            countryCode: form.countryCode.value,
            operatorCode: form.operatorCode.value,
            phoneNumber: form.phoneNumber.value,
            phoneType: form.phoneType.value,
            phoneComment: form.phoneComment.value
        };
    }

    function getAttachmentData() {
        var form = $('#upload-attachment-form');
        return {
            fileName: form.fileName.value,
            uploadDate: form.uploadDate.value,
            attachmentComment: form.attachmentComment
        }
    }

    function submitPhotoHandler(e) {
        e.preventDefault();
        addOptionalCommandToForm('#contact-edit-form', 'uploadPhoto');
        toggleUploadPhotoModal();
    }

    function submitPhoneNumberHandler() {
        if (isElementExist('#phone-numbers-empty-table')) {
            $('#contact-edit-form').replaceChild(generatePhoneNumberEmptyList(), $('#phone-numbers-empty-table'))
        }
        var formData = getPhoneNumberData();
        $('#phone-number-rows').innerHTML += generatePhoneRecord(phoneNumberCounter++, formData);
        togglePhoneNumberModal();
    }

    function submitAddAttachmentHandler() {
        if (isElementExist('#attachments-empty-table')) {
            $('#contact-edit-form').replaceChild(generateAttachmentEmptyList(), $('#attachments-empty-table'))
        }
    }

    function togglePhoneNumberModal() {
        toggleClass($('#modal-phone-edit'), 'jlab-fade');
    }

    function toggleAttachmentsEditModal() {
        toggleClass($('#modal-attachment-edit'), 'jlab-fade');
    }

    function toggleAttachmentsUploadModal() {
        toggleClass($('#modal-upload-attachment'), 'jlab-fade');
    }

    function toggleUploadPhotoModal() {
        toggleClass($('#modal-upload-photo'), 'jlab-fade');
    }

    function generatePhoneRecord(counter, data) {
        return '<ul class="jlab-row">' +
            '<li class="jlab-cell-3">' +
            '<input type="checkbox" name="selectedId" value="" id="PhoneNumberId' + counter + '" data-action="deletePhone">' +
            '<input class="jlab-hidden" type="text" name="phoneNumberId" value="">' +

            '<label for="PhoneNumberId' + counter + '"> +' + data.countryCode + ' (' + data.operatorCode + ') '
            + data.phoneNumber + '</label>' +

            '<input class="jlab-hidden" type="text" name="countryCode" value="'
            + data.countryCode + '">' +

            '<input class="jlab-hidden" type="text" name="operatorCode" value="'
            + data.operatorCode + '">' +

            '<input class="jlab-hidden" type="text" name="number" value="' + data.phoneNumber + '">' +
            '</li>' +

            '<li class="jlab-cell-2">' + data.phoneType +
            '<input class="jlab-hidden" type="text" name="phoneType" value="' + data.phoneType + '">' +
            '</li>' +

            '<li class="jlab-cell-7">' + data.phoneComment +
            '<input class="jlab-hidden" type="text" name="comment" value="' + data.phoneComment + '">' +
            '</li>' +
            '</ul>'
    }

    function generateAttachmentRecord(counter, data) {
        var ul = document.createElement('ul');
        ul.className = 'jlab-row';
        ul.innerHTML = '<li class="jlab-cell-3">' +
            '<input class="jlab-hidden" type="text" name="attachmentId" value="">' +
            '<input type="checkbox" id="attachment-' + counter + '">' +
            '<label for="attachment-' + counter + '">' + data.fileName + '</label>' +
            '</li>' +
            '<li class="jlab-cell-2">' +
            data.uploadDate +
            '<input class="jlab-hidden" type="text" name="uploadDate" value="' + data.uploadDate + '">' +
            '</li>' +
            '<li class="jlab-cell-7">' +
            data.attachmentComment +
            '<input class="jlab-hidden" type="text" name="attachmentComment" value="' + data.attachmentComment + '">' +
            '</li>';
        return ul;
    }

    function generatePhoneNumberEmptyList() {
        var list = document.createElement('div');
        list.className = "jlab-inner-table-container";
        list.id = "inner-phone-number-table";
        list.innerHTML =
            '<ul class="jlab-row jlab-inner-table-column-caption">' +
            '<li class="jlab-cell-3">Phone number</li>' +
            '<li class="jlab-cell-2">Type</li>' +
            '<li class="jlab-cell-7">Comment</li>' +
            '</ul>' +
            '<div id="phone-number-rows">' +
            '</div>';
        return list;
    }

    function generateAttachmentEmptyList() {
        var list = document.createElement('div');
        list.className = "jlab-inner-table-container";
        list.id = "inner-attachment-table";
        list.innerHTML =
            '<ul class="jlab-row jlab-inner-table-column-caption">' +
            '<li class="jlab-cell-3">File Name</li>' +
            '<li class="jlab-cell-2">Upload Date</li>' +
            '<li class="jlab-cell-7">Comment</li>' +
            '</ul>' +
            '<div id="attachment-rows">' +
            '</div>';
        return list;
    }

    function generateHiddenInput(name, value) {
        var element = document.createElement('div');
        element.innerHTML = '<input class="jlab-hidden" name="' + name + '" value="' + value + '">';
        return element.children[0];
    }

    function addOptionalCommandToForm(formId, command) {
        $(formId).appendChild(generateHiddenInput('optionalCommand', command));
    }

    function deleteCheckedPhoneNumbers() {
        addOptionalCommandToForm('#contact-edit-form', 'deletePhone');
        var checkboxes = $all('[data-action="deletePhone"]:checked');
        for (var i = 0; i < checkboxes.length; i++) {
            hide(checkboxes[i].parentNode.parentNode);
        }
    }


// listeners
    $('#button-upload-photo').addEventListener('click', submitPhotoHandler, false);
    $('#contact-photo-container').addEventListener('click', toggleUploadPhotoModal, false);
    $('#button-cancel-upload-photo').addEventListener('click', toggleUploadPhotoModal, false);
    $('#button-save-phone-number').addEventListener('click', submitPhoneNumberHandler, false);
    $('#button-show-phone-number-modal').addEventListener('click', togglePhoneNumberModal, false);
    $('#button-cancel-phone-number-add').addEventListener('click', togglePhoneNumberModal, false);
    $('#button-show-attachments-edit-modal').addEventListener('click', toggleAttachmentsEditModal, false);
    $('#button-cancel-attachments-edit-modal').addEventListener('click', toggleAttachmentsEditModal, false);
    $('#button-show-attachments-upload-modal').addEventListener('click', toggleAttachmentsUploadModal, false);
    $('#button-cancel-upload-attachment').addEventListener('click', toggleAttachmentsUploadModal, false);
    $('#button-upload-attachment').addEventListener('click', submitAddAttachmentHandler, false);
    $('#button-delete-phone-number').addEventListener('click', deleteCheckedPhoneNumbers, false);
    $('#contact-edit-form').addEventListener('keyup', formValidation, false);
    $('#contact-edit-form').addEventListener('input', formValidation, false);

    var phoneNumberCounter = (isElementExist('#phone-number-rows')) ? $('#phone-number-rows').childElementCount : 0;
    var attachmentCounter = (isElementExist('#attachment-rows')) ? $('#attachment-rows').childElementCount : 0;
})();