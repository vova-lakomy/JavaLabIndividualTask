(function () {
    function getPhoneNumberData(formSelector) {
        var form = $(formSelector);
        if (form.phoneNumber.value) {
            return {
                counter: (form.phoneNumberCounter) ? form.phoneNumberCounter.value : '',
                phoneNumberId: form.phoneNumberId.value,
                countryCode: form.countryCode.value,
                operatorCode: form.operatorCode.value,
                phoneNumber: form.phoneNumber.value,
                phoneType: form.phoneType.value,
                phoneComment: form.phoneComment.value
            };
        } else {
            return false
        }
    }

    function getAttachmentData(formSelector) {
        var form = $(formSelector);
        if (form.attachedFile.files.length) {
            var originalFileName = form.attachedFile.files[0].name;
            var ext = originalFileName.lastIndexOf('.') ? originalFileName.substring(originalFileName.lastIndexOf('.')) : '';
            return {
                counter: (form.attachmentCounter) ? form.attachmentCounter.value : '',
                attachmentId: (form.attachmentId) ? form.attachmentId.value : '',
                fileName: form.attachedFileName.value ? (form.attachedFileName.value + ext) : originalFileName,
                uploadDate: new Date(),
                attachmentComment: form.attachedFileComment.value,
                attachedFileNode: form.attachedFile
            }
        } else {
            return false;
        }
    }

    function getAttachmentEditData(formSelector) {
        var form = $(formSelector);
        var link = form.attachmentLink.value;
        var lastSeparatorIndex = link.lastIndexOf('/')+1;
        var oldFileName = link.substring(lastSeparatorIndex);
        var newFileLink = link.replace(oldFileName,form.attachmentName.value);
        return {
            counter: (form.attachmentCounter) ? form.attachmentCounter.value : '',
            attachmentId: (form.attachmentId) ? form.attachmentId.value : '',
            fileName: form.attachmentName.value,
            attachmentLink: newFileLink,
            uploadDate: form.uploadDate.value,
            attachmentComment: form.attachmentComment.value,
            oldFileName: oldFileName,
        }
    }

    function submitPhotoHandler(e) {
        e.preventDefault();
        addOptionalCommandToForm('#contact-edit-form', 'uploadPhoto');
        toggleUploadPhotoModal();
    }

    function addPhoneNumberHandler(e) {
        e.preventDefault(); // don't submit form
        var formData = getPhoneNumberData('#phone-add-form');
        if (formData) {
            if (hasClass($('#inner-phone-number-table'), 'jlab-hidden')) {
                addClass($('#phone-numbers-empty-table'), 'jlab-hidden');
                removeClass($('#inner-phone-number-table'), 'jlab-hidden');
            }
            $('#phone-number-rows').appendChild(generatePhoneRecord(phoneNumberCounter++, formData));
        }
        toggleAddPhoneNumberModal();
    }

    function addAttachmentHandler() {
        var data = getAttachmentData('#upload-attachment-form');
        if (data) {
            addOptionalCommandToForm('#contact-edit-form', 'uploadAttachment');
            if (hasClass($('#inner-attachment-table'), 'jlab-hidden')) {
                addClass($('#attachments-empty-table'), 'jlab-hidden');
                removeClass($('#inner-attachment-table'), 'jlab-hidden');
            }
            $('#attachment-rows').appendChild(generateAttachmentRecord(attachmentCounter++, data));
        }
        toggleAttachmentsUploadModal();
    }

    function toggleAddPhoneNumberModal() {
        toggleClass($('#modal-phone-add'), 'jlab-fade');
        $('#phone-add-form').reset();
    }

    function toggleEditPhoneNumberModal() {
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
        var ul = document.createElement('ul');
        ul.className = 'jlab-row';
        ul.innerHTML =
            '<li class="jlab-cell-4">' +
            '<input class="jlab-hidden" type="text" name="phoneNumberId" value="' + data.phoneNumberId + '">' +
            '<input class="jlab-hidden" type="text" name="countryCode" value="' + data.countryCode + '">' +
            '<input class="jlab-hidden" type="text" name="operatorCode" value="' + data.operatorCode + '">' +
            '<input class="jlab-hidden" type="text" name="number" value="' + data.phoneNumber + '"> ' +
            '<input class="jlab-hidden" type="text" name="phoneType" value="' + data.phoneType + '">' +
            '<input class="jlab-hidden" type="text" name="comment" value="' + data.phoneComment + '">' +

            '<input type="checkbox" name="selectedId" value="" id="PhoneNumberId-' + counter + '" data-action="deletePhone">' +
            '<label for="PhoneNumberId-' + counter + '"> +' + data.countryCode + ' (' + data.operatorCode + ') '
            + data.phoneNumber + '&nbsp;&nbsp;</label> ' +
            '<img class="jlab-edit-image" src="../resources/img/pencil_12x12.png" title="edit" data-action="edit">' +
            '</li>' +

            '<li class="jlab-cell-2">' + data.phoneType + '</li>' +

            '<li class="jlab-cell-6">' + data.phoneComment + '</li>';
        return ul;
    }

    function generateAttachmentRecord(counter, data) {
        if (data.attachedFileNode) {
            var fileNode = data.attachedFileNode.cloneNode();
            var emptyList = fileNode.files;
            fileNode.files = data.attachedFileNode.files;
            data.attachedFileNode.files = emptyList;
            fileNode.id = 'attachedFileId-' + counter;
            fileNode.className = 'jlab-hidden';
            fileNode.name = 'attachedFile-' + counter;
        }
        var ul = document.createElement('ul');
        ul.className = 'jlab-row';
        ul.innerHTML =
            '<li class="jlab-cell-4">' +
            '<input class="jlab-hidden" type="text" name="attachmentId-' + counter + '" value="' + data.attachmentId + '">' +
            '<input class="jlab-hidden" type="text" name="attachmentFileName-' + counter + '" value="' + data.fileName + '"/>' +
            '<input class="jlab-hidden" type="text" name="attachmentComment-' + counter + '" value="' + data.attachmentComment + '">' +
            '<input class="jlab-hidden" type="text" name="uploadDate-' + counter + '" value="' + data.uploadDate + '">' +

            '<input type="checkbox" id="attachment-' + counter + '" data-action="deleteAttachment">' +
            '<label class="jlab-not-submited" for="attachedFileId-' + counter + '" title="submit to upload">' + data.fileName + '</label>' +
            '</li>' +

            '<li class="jlab-cell-2">' +
            '<span class="jlab-not-submited">not submited</span> ' +
            '</li>' +

            '<li class="jlab-cell-6">' +
            '<span class="jlab-not-submited">' + data.attachmentComment + '</span> ' +
            '</li>';
        if (fileNode) {
            ul.appendChild(fileNode);
        }
        return ul;
    }

    function generateAttachmentEditedRecord(counter, data) {
        var ul = document.createElement('ul');
        ul.className = 'jlab-row';
        ul.innerHTML =
            '<li class="jlab-cell-4">' +
            '<input class="jlab-hidden" type="text" name="attachmentId-' + counter + '" value="' + data.attachmentId + '">' +
            '<input class="jlab-hidden" type="text" name="attachmentLink-' + counter + '" value="' + data.attachmentLink + '"/>' +
            '<input class="jlab-hidden" type="text" name="attachmentFileName-' + counter + '" value="' + data.fileName + '"/>' +
            '<input class="jlab-hidden" type="text" name="attachmentOldFileName-' + counter + '" value="' + data.oldFileName + '"/>' +
            '<input class="jlab-hidden" type="text" name="attachmentComment-' + counter + '" value="' + data.attachmentComment + '">' +
            '<input class="jlab-hidden" type="text" name="uploadDate-' + counter + '" value="' + data.uploadDate + '">' +

            '<input type="checkbox" id="attachment-' + counter + '" data-action="deleteAttachment">' +
            '<label for="attachedFileId-' + counter + '" title="submit to upload">' + data.fileName + '</label>' +
            '</li>' +

            '<li class="jlab-cell-2">' +
             data.uploadDate +
            '</li>' +

            '<li class="jlab-cell-6">' +
            '<span >' + data.attachmentComment + '</span> ' +
            '</li>';
        return ul;
    }

    function generatePhoneNumberEmptyList() {
        var list = document.createElement('div');
        list.className = "jlab-inner-table-container";
        list.id = "inner-phone-number-table";
        list.innerHTML =
            '<ul class="jlab-row jlab-inner-table-column-caption">' +
            '<li class="jlab-cell-4">Phone number</li>' +
            '<li class="jlab-cell-2">Type</li>' +
            '<li class="jlab-cell-6">Comment</li>' +
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
            '<li class="jlab-cell-4">File Name</li>' +
            '<li class="jlab-cell-2">Upload Date</li>' +
            '<li class="jlab-cell-6">Comment</li>' +
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

    function addOptionalCommandToForm(formSelector, command) {
        $(formSelector).appendChild(generateHiddenInput('optionalCommand', command));
    }

    function deleteCheckedPhoneNumbers() {
        addOptionalCommandToForm('#contact-edit-form', 'deletePhone');
        var checkboxes = $all('[data-action="deletePhone"]:checked');
        for (var i = 0; i < checkboxes.length; i++) {
            hide(checkboxes[i].parentNode.parentNode);
        }
    }

    function deleteCheckedAttachments() {
        addOptionalCommandToForm('#contact-edit-form', 'deleteAttachment');
        var checkboxes = $all('[data-action="deleteAttachment"]:checked');
        for (var i = 0; i < checkboxes.length; i++) {
            hide(checkboxes[i].parentNode.parentNode);
        }
    }

    function collectPhoneDataFromInputs(node) {
        var data = {};
        for (var i = 0; i < node.children.length; i++) {
            switch (node.children[i].name) {
                case 'phoneNumberId':
                    data.phoneNumberId = (node.children[i].value === null) ? '' : node.children[i].value;
                    break;
                case 'countryCode' :
                    data.countryCode = node.children[i].value;
                    break;
                case 'operatorCode' :
                    data.operatorCode = node.children[i].value;
                    break;
                case 'number' :
                    data.number = node.children[i].value;
                    break;
                case 'phoneType' :
                    data.phoneType = node.children[i].value;
                    break;
                case 'comment' :
                    data.comment = node.children[i].value;
                    break;
                case 'selectedId' :
                    var counter = node.children[i].id;
                    data.counter = counter.substring(counter.indexOf('-') + 1);
                    break;
            }
        }
        return data;
    }

    function collectAttachmentDataFromInputs(node) {
        var data = {};
        for (var i = 0; i < node.children.length; i++) {
            if (node.children[i].name.indexOf('attachmentFileName') !== -1) {
                data.attachmentName = node.children[i].value;
                var counter = node.children[i].name;
                data.counter = counter.substring(counter.indexOf('-') + 1);
            } else if (node.children[i].name.indexOf('attachmentComment') !== -1) {
                data.attachmentComment = node.children[i].value;
            } else if (node.children[i].name.indexOf('attachmentLink') !== -1) {
                data.attachmentLink = node.children[i].value;
            } else if (node.children[i].name.indexOf('attachmentId') !== -1) {
                data.attachmentId = node.children[i].value;
            } else if (node.children[i].name.indexOf('uploadDate') !== -1) {
                data.uploadDate = node.children[i].value;
            }
        }
        return data;
    }

    function fillPhoneEditModalForm(event) {
        if (event.target.getAttribute("data-action") === 'edit') {
            var form = $('#phone-edit-form');
            var data = collectPhoneDataFromInputs(event.target.parentNode);
            window.editableInput = event.target.parentNode.parentNode;
            form['phoneNumberCounter'].value = data.counter;
            form['phoneNumberId'].value = data.phoneNumberId;
            form['countryCode'].value = data.countryCode;
            form['operatorCode'].value = data.operatorCode;
            form['phoneNumber'].value = data.number;
            form['phoneType'].value = data.phoneType;
            form['phoneComment'].value = data.comment;
            toggleEditPhoneNumberModal();
        }
    }

    function saveEditedPhoneNumber() {
        var data = getPhoneNumberData('#phone-edit-form');
        editableInput.parentNode.replaceChild(generatePhoneRecord(data.counter, data), editableInput);
        toggleEditPhoneNumberModal();
    }

    function saveEditedAttachment() {
        debugger;
        addOptionalCommandToForm('#contact-edit-form', 'renameAttachment');
        data = getAttachmentEditData('#modal-attachment-edit-form');
        var record = generateAttachmentEditedRecord(data.counter, data);
        editableInput.parentNode.replaceChild(record, editableInput);
        toggleAttachmentsEditModal();
    }

    function fillAttachmentEditModalForm(event) {
        if (event.target.getAttribute("data-action") === 'edit') {
            var form = $('#modal-attachment-edit-form');
            var data = collectAttachmentDataFromInputs(event.target.parentNode);
            window.editableInput = event.target.parentNode.parentNode;
            form['attachmentName'].value = data.attachmentName;
            form['attachmentComment'].value = data.attachmentComment;
            form['attachmentCounter'].value = data.counter;
            form['attachmentLink'].value = data.attachmentLink;
            form['attachmentId'].value = data.attachmentId;
            form['uploadDate'].value = data.uploadDate;
            toggleAttachmentsEditModal();
        }
    }


// listeners
    $('#button-upload-photo').addEventListener('click', submitPhotoHandler, false);
    $('#contact-photo-container').addEventListener('click', toggleUploadPhotoModal, false);
    $('#button-cancel-upload-photo').addEventListener('click', toggleUploadPhotoModal, false);
    $('#button-add-phone-number').addEventListener('click', addPhoneNumberHandler, false);
    $('#button-save-phone-number').addEventListener('click', saveEditedPhoneNumber, false);
    $('#button-show-phone-number-modal').addEventListener('click', toggleAddPhoneNumberModal, false);
    $('#button-cancel-phone-number-add').addEventListener('click', toggleAddPhoneNumberModal, false);
    $('#button-cancel-phone-number-edit').addEventListener('click', toggleEditPhoneNumberModal, false);
    // $('#button-show-attachments-edit-modal').addEventListener('click', toggleAttachmentsEditModal, false);
    $('#button-cancel-attachments-edit-modal').addEventListener('click', toggleAttachmentsEditModal, false);
    $('#button-show-attachments-upload-modal').addEventListener('click', toggleAttachmentsUploadModal, false);
    $('#button-cancel-upload-attachment').addEventListener('click', toggleAttachmentsUploadModal, false);
    $('#button-upload-attachment').addEventListener('click', addAttachmentHandler, false);
    $('#button-delete-phone-number').addEventListener('click', deleteCheckedPhoneNumbers, false);
    $('#button-delete-attachment').addEventListener('click', deleteCheckedAttachments, false);
    $('#button-save-attachment-edit-modal').addEventListener('click', saveEditedAttachment, false);
    $('#inner-phone-number-table').addEventListener('click', fillPhoneEditModalForm, false);
    $('#inner-attachment-table').addEventListener('click', fillAttachmentEditModalForm, false);

//validation listeners
    $('#contact-edit-form').addEventListener('keyup', formValidation, false);
    $('#contact-edit-form').addEventListener('input', formValidation, false);
    $('#phone-add-form').addEventListener('input', formValidation, false);
    $('#phone-add-form').addEventListener('input', formValidation, false);

//counters
    var phoneNumberCounter = (isElementExist('#phone-number-rows')) ? $('#phone-number-rows').childElementCount : 0;
    var attachmentCounter = (isElementExist('#attachment-rows')) ? $('#attachment-rows').childElementCount : 0;
})();