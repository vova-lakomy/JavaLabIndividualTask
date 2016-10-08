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
        var counter = attachmentCounter - 1;
        var fileInputNodeSelector = '#attachedFileId-' + counter;
        var fileInputNode = $(fileInputNodeSelector);
        var form = $(formSelector);
        if (fileInputNode.files.length) {
            var originalFileName = fileInputNode.files[0].name;
            var ext = originalFileName.lastIndexOf('.') ? originalFileName.substring(originalFileName.lastIndexOf('.')) : '';
            var isoDate = new Date().toISOString().split('-');
            var day = isoDate[2].split('T')[0];
            var month = isoDate[1];
            var year = isoDate[0];
            var uploadDate = day + '.' + month + '.'  + year;
            return {
                counter: (form.attachmentCounter) ? form.attachmentCounter.value : '',
                attachmentId: (form.attachmentId) ? form.attachmentId.value : '',
                fileName: form.attachedFileName.value ? (form.attachedFileName.value + ext) : originalFileName,
                uploadDate: uploadDate,
                attachmentComment: form.attachedFileComment.value,
                attachedFileNode: fileInputNode,
            }
        } else {
            return false;
        }
    }

    function getAttachmentEditData(formSelector) {
        var form = $(formSelector);
        var link = form.attachmentLink.value;
        var oldFileName = form.attachmentOldName.value;
        var newFileLink = link.replace(oldFileName, form.attachmentName.value);
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
        var fileInputNode = $('#photo-file-input');
        if (fileInputNode.files.length){
            var image = fileInputNode.files[0];
            showImageThumbnail(image);
            if (hasClass($('#main-photo-blind'),'jlab-fade')){
                removeClass($('#main-photo-blind'),'jlab-fade');
            }
        }
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
        var rowId = "#attachment-row-" + (attachmentCounter - 1);
        if (data) {
            if (hasClass($('#inner-attachment-table'), 'jlab-hidden')) {
                addClass($('#attachments-empty-table'), 'jlab-hidden');
                removeClass($('#inner-attachment-table'), 'jlab-hidden');
            }
            addOptionalCommandToForm('#contact-edit-form', 'uploadAttachment');
            fillAttachmentRowFields(data);
            removeClass($(rowId), 'jlab-hidden');
        } else {
            var row = $(rowId);
            row.parentNode.removeChild(row);
        }
        toggleAttachmentsUploadModal();
        resetUploadAttachmentModal();
    }

    function resetUploadAttachmentModal() {
        $('#upload-attachment-form').reset();
        $('#label-attachment-upload').innerHTML = 'no file selected';
    }

    function fillAttachmentRowFields(data) {
        var counter = attachmentCounter - 1;
        var idInput = $('#attachment-row-input-id-' + counter);
        var fileNameInput = $('#attachment-row-input-fileName-' + counter);
        var commentInput = $('#attachment-row-input-comment-' + counter);
        var uploadDateInput = $('#attachment-row-input-uploadDate-' + counter);
        var fileNameLabel = $('#attachment-row-label-filename-' + counter);
        var commentTextarea = $('#attachment-row-textarea-' + counter);
        idInput.value = null;
        fileNameInput.value = data.fileName;
        commentInput.value = data.attachmentComment;
        uploadDateInput.value = data.uploadDate;
        fileNameLabel.innerHTML = data.fileName;
        commentTextarea.value = data.attachmentComment;
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

    function openAttachmentsUploadModal(e) {
        e.preventDefault();
        var counter = attachmentCounter++;
        var ulNode = generateAttachmentRecord(counter);
        $('#attachment-rows').appendChild(ulNode);
        $('#attachedFileId-' + counter).addEventListener('change', handleChosenFile, false);
        $('#label-attachment-file-input').setAttribute('for','attachedFileId-'+counter);
        toggleClass($('#modal-upload-attachment'), 'jlab-fade');
    }

    function closeAttachmentsUploadModal() {
        var rowId = "#attachment-row-" + (attachmentCounter - 1);
        if (rowId) {
            var row = $(rowId);
            row.parentNode.removeChild(row);
        }
        toggleAttachmentsUploadModal();
        resetUploadAttachmentModal();
    }

    function toggleAttachmentsUploadModal() {
        toggleClass($('#modal-upload-attachment'), 'jlab-fade');
    }


    function toggleUploadPhotoModal() {
        toggleClass($('#modal-upload-photo'), 'jlab-fade');
    }

    function cancelUploadPhotoModal() {
        toggleClass($('#modal-upload-photo'), 'jlab-fade');
        $('#photo-file-input').value = '';
    }

    function generatePhoneRecord(counter, data) {
        var ul = document.createElement('ul');
        ul.className = 'jlab-row jlab-vertical-align-top';
        ul.innerHTML =
            '<li class="jlab-cell-4">' +
            '<input class="jlab-hidden" type="text" name="phoneNumberId" value="' + data.phoneNumberId + '">' +
            '<input class="jlab-hidden" type="text" name="countryCode" value="' + data.countryCode + '">' +
            '<input class="jlab-hidden" type="text" name="operatorCode" value="' + data.operatorCode + '">' +
            '<input class="jlab-hidden" type="text" name="number" value="' + data.phoneNumber + '"> ' +
            '<input class="jlab-hidden" type="text" name="phoneType" value="' + data.phoneType + '">' +
            '<textarea class="jlab-hidden" name="comment">' + data.phoneComment + '</textarea>' +

            '<input type="checkbox" name="selectedId" value="" id="PhoneNumberId-' + counter + '" data-action="deletePhone">' +
            '<label for="PhoneNumberId-' + counter + '"> +' + data.countryCode + ' (' + data.operatorCode + ') '
            + data.phoneNumber + '&nbsp;&nbsp;</label> ' +
            '<img class="jlab-edit-image" src="../resources/img/pencil_12x12.png" title="edit" data-action="edit">' +
            '</li>' +

            '<li class="jlab-cell-2">' + data.phoneType + '</li>' +

            '<li class="jlab-cell-6">' +
            '<textarea readonly class="jlab-textarea-read-only">' + data.phoneComment + '</textarea>' +
            '</li>';
        return ul;
    }

    function generateAttachmentRecord(counter) {
        var ul = document.createElement('ul');
        ul.id = 'attachment-row-' + counter;
        ul.className = 'jlab-row jlab-hidden';
        ul.innerHTML =
            '<li class="jlab-cell-4 jlab-attachment-file-name">' +
            '<input id="attachment-row-input-id-' + counter + '" class="jlab-hidden" type="text" name="attachmentId-' + counter + '" value="">' +
            '<input id="attachment-row-input-fileName-' + counter + '" class="jlab-hidden" type="text" name="attachmentFileName-' + counter + '" value=""/>' +
            '<textarea id="attachment-row-input-comment-' + counter + '" class="jlab-hidden" ' +
            ' name="attachmentComment-' + counter + '"></textarea>' +
            '<input id="attachment-row-input-uploadDate-' + counter + '" class="jlab-hidden" type="text" name="uploadDate-' + counter + '" value="">' +
            '<input type="checkbox" name="selectedId" value="" id="attachment-' + counter + '" data-action="deleteAttachment">' +
            '<a href="#" ' +
            '<label id="attachment-row-label-fileName-' + counter + '" class="jlab-not-submited" title="submit to upload"></label>' +
            '</a>' +
            '</li>' +
            '<li class="jlab-cell-2 jlab-attachment-upload-date">' +
            '<span class="jlab-not-submited">not submited</span> ' +
            '</li>' +
            '<li class="jlab-cell-6">' +
            '<textarea readonly id="attachment-row-textarea-' + counter + '" class="jlab-textarea-read-only jlab-not-submited"></textarea> ' +
            '</li>' +
            '<input type="file" id="attachedFileId-' + counter + '" class="jlab-hidden" name="attachedFile-' + counter + '">';
        return ul;
    }

    function generateAttachmentEditedRecord(counter, data) {
        var ul = document.createElement('ul');
        ul.className = 'jlab-row';
        ul.innerHTML =
            '<li class="jlab-cell-4 jlab-attachment-file-name">' +
            '<input class="jlab-hidden" type="text" name="attachmentId-' + counter + '" value="' + data.attachmentId + '">' +
            '<input class="jlab-hidden" type="text" name="attachmentLink-' + counter + '" value="' + data.attachmentLink + '"/>' +
            '<input class="jlab-hidden" type="text" name="attachmentFileName-' + counter + '" value="' + data.fileName + '"/>' +
            '<input class="jlab-hidden" type="text" name="attachmentOldFileName-' + counter + '" value="' + data.oldFileName + '"/>' +
            '<textarea class="jlab-hidden" name="attachmentComment-' + counter + '">' + data.attachmentComment + '</textarea>' +
            '<input class="jlab-hidden" type="text" name="uploadDate-' + counter + '" value="' + data.uploadDate + '">' +

            '<input type="checkbox" name="selectedId" value="" id="attachment-' + counter + '" data-action="deleteAttachment">' +
            '<a href="#">' +
            '<label for="attachedFileId-' + counter + '" title="submit to save changes">' + data.fileName + '</label>' +
            '</a>' +
            '</li>' +

            '<li class="jlab-cell-2 jlab-attachment-upload-date">' +
            data.uploadDate +
            '</li>' +

            '<li class="jlab-cell-6">' +
            '<textarea readonly class="jlab-textarea-read-only">' + data.attachmentComment + '</textarea> ' +
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
            var checkedBox = checkboxes[i];
            var checkedPhone = checkboxes[i].parentNode.parentNode;
            var checkedPhoneParent = checkedPhone.parentNode;
            if (checkedPhoneHasId(checkedBox)) {
                hide(checkedBox.parentNode.parentNode);
            } else {
                checkedPhoneParent.removeChild(checkedPhone)
            }
        }
    }

    function checkedPhoneHasId(checkboxNode) {
        return checkboxNode.value;
    }

    function deleteCheckedAttachments() {
        addOptionalCommandToForm('#contact-edit-form', 'deleteAttachment');
        var checkboxes = $all('[data-action="deleteAttachment"]:checked');
        for (var i = 0; i < checkboxes.length; i++) {
            var checkedBox = checkboxes[i];
            var checkedAttachment = checkboxes[i].parentNode.parentNode;
            var checkedAttachmentParent = checkedAttachment.parentNode;
            if (checkedAttachmentHasId(checkedBox)) {
                hide(checkboxes[i].parentNode.parentNode);
            } else {
                checkedAttachmentParent.removeChild(checkedAttachment);
            }
        }
    }

    function checkedAttachmentHasId(checkboxNode) {
        return checkboxNode.value;
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
                data.attachmentOldName = data.attachmentName;
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

    function saveEditedPhoneNumber(e) {
        e.preventDefault();
        var data = getPhoneNumberData('#phone-edit-form');
        editableInput.parentNode.replaceChild(generatePhoneRecord(data.counter, data), editableInput);
        toggleEditPhoneNumberModal();
    }

    function saveEditedAttachment() {
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
            form['attachmentOldName'].value = data.attachmentOldName;
            toggleAttachmentsEditModal();
        }
    }

    function handleChosenImage() {
        var fileInputNode = $('#photo-file-input');
        if (fileInputNode.files.length) {
            var image = fileInputNode.files[0];
            if(!isImageValid(image)){
                disableButton('#button-upload-photo')
            } else {
                enableButton('#button-upload-photo')
            }
            var fileName = image.name;
            var fileSize = image.size;
            $('#input-file-name').innerHTML = fileName + ' ' + Math.ceil(fileSize/1000) + '&nbsp;kb';
        } else {
            $('#input-file-name').innerHTML = 'no file selected'
        }
    }

    function isImageValid(file) {
        var fileType = file.type.substring(0,file.type.indexOf('/'));
        if (file.size > 0 && file.size < 10485759 && fileType === 'image'){
            return true;
        } else {
            return false;
        }
    }

    function showImageThumbnail(image) {
        if (isImageValid(image)){
            var imageNode = $('#contact-image');
            imageNode.file = image;
            var reader = new FileReader();
            reader.onload = (function(aImg) { return function(e) { aImg.src = e.target.result; }; })(imageNode);
            reader.readAsDataURL(image);
        }


    }

    function disableButton(selector) {
        $(selector).setAttribute('disabled','disabled');
    }

    function enableButton(selector) {
        $(selector).removeAttribute('disabled');
    }

    function handleChosenFile() {
        var counter = attachmentCounter - 1;
        var attachmentSelector = '#attachedFileId-' + counter;
        var fileInputNode = $(attachmentSelector);
        if (fileInputNode.files.length) {
            if(!isFileValid(fileInputNode.files[0])){
                disableButton('#button-upload-attachment')
            } else {
                enableButton('#button-upload-attachment')
            }
            var fileName = fileInputNode.files[0].name;
            var fileSize = fileInputNode.files[0].size;

            var labelText = fileName + ' ' + Math.ceil(fileSize/1000) + '&nbsp;kb';
            if (labelText.length > 35) {
                labelText = labelText.substring(0,12) + ' ... ' + labelText.substring((labelText.length)-24);
            }
            $('#label-attachment-upload').innerHTML = labelText;
        } else {
            $('#label-attachment-upload').innerHTML = 'no file selected'
        }
    }

    function isFileValid(file) {
        return (file.size > 0 && file.size < 10485759);
    }

    function handleUploadFileNameChange(e) {
        if (e.target.value.length > 100){
            disableButton('#button-upload-attachment');
        } else {
            enableButton('#button-upload-attachment');
        }
    }

    function handleUploadCommentChange(e){
        if (e.target.value.length > 900){
            disableButton('#button-upload-attachment');
        } else {
            enableButton('#button-upload-attachment');
        }
    }

    function handleEditFileNameChange(e) {
        if (e.target.value.length > 100){
            disableButton('#button-save-attachment-edit-modal');
        } else {
            enableButton('#button-save-attachment-edit-modal');
        }
    }

    function handleEditCommentChange(e){
        if (e.target.value.length > 900){
            disableButton('#button-save-attachment-edit-modal');
        } else {
            enableButton('#button-save-attachment-edit-modal');
        }
    }

    function expandTextarea(element) {
        element.style.height = element.scrollHeight + "px";
    }

// listeners
    addEvents( [
        {
            selector : '#button-upload-photo',
            event : 'click',
            handler : submitPhotoHandler,
        },
        {
            selector : '#contact-photo-container',
            event : 'click',
            handler : toggleUploadPhotoModal,
        },
        {
            selector : '#button-cancel-upload-photo',
            event : 'click',
            handler : cancelUploadPhotoModal,
        },
        {
            selector : '#button-add-phone-number',
            event : 'click',
            handler : addPhoneNumberHandler,
        },
        {
            selector : '#button-save-phone-number',
            event : 'click',
            handler : saveEditedPhoneNumber,
        },
        {
            selector : '#button-show-phone-number-modal',
            event : 'click',
            handler : toggleAddPhoneNumberModal,
        },
        {
            selector : '#button-cancel-phone-number-add',
            event : 'click',
            handler : toggleAddPhoneNumberModal,
        },
        {
            selector : '#button-cancel-phone-number-edit',
            event : 'click',
            handler : toggleEditPhoneNumberModal,
        },
        {
            selector : '#button-cancel-attachments-edit-modal',
            event : 'click',
            handler : toggleAttachmentsEditModal,
        },
        {
            selector : '#button-show-attachments-upload-modal',
            event : 'click',
            handler : openAttachmentsUploadModal,
        },
        {
            selector : '#button-cancel-upload-attachment',
            event : 'click',
            handler : closeAttachmentsUploadModal,
        },
        {
            selector : '#button-upload-attachment',
            event : 'click',
            handler : addAttachmentHandler,
        },
        {
            selector : '#button-delete-phone-number',
            event : 'click',
            handler : deleteCheckedPhoneNumbers,
        },

        {
            selector : '#button-delete-attachment',
            event : 'click',
            handler : deleteCheckedAttachments,
        },
        {
            selector : '#button-save-attachment-edit-modal',
            event : 'click',
            handler : saveEditedAttachment,
        },
        {
            selector : '#inner-phone-number-table',
            event : 'click',
            handler : fillPhoneEditModalForm,
        },
        {
            selector : '#inner-attachment-table',
            event : 'click',
            handler : fillAttachmentEditModalForm,
        },
        {
            selector : '#photo-file-input',
            event : 'change',
            handler : handleChosenImage,
        },
        {
            selector : '#attachment-file-input',
            event : 'change',
            handler : handleChosenFile,
        },
        {
            selector : '#attachment-file-name',
            event : 'keyup input',
            handler : handleUploadFileNameChange,
        },
        {
            selector : '#attachment-file-comment',
            event : 'keyup input',
            handler : handleUploadCommentChange,
        },
        {
            selector : '#attachment-name',
            event : 'keyup input',
            handler : handleEditFileNameChange,
        },
        {
            selector : '#attachment-comment',
            event : 'keyup input',
            handler : handleEditCommentChange,
        },
        {
            selector : '#hrefAddAttachment',
            event : 'click',
            handler : openAttachmentsUploadModal,
        },
        {
            selector : '#contact-edit-form',
            event : 'keyup input',
            handler : formValidation,
        },
        {
            selector : '#phone-add-form',
            event : 'keyup input',
            handler : formValidation,
        },
        {
            selector : '#phone-edit-form',
            event : 'keyup input',
            handler : formValidation,
        },
    ]);

//counters
    var phoneNumberCounter = (isElementExist('#phone-number-rows')) ? $('#phone-number-rows').childElementCount : 0;
    var attachmentCounter = (isElementExist('#attachment-rows')) ? $('#attachment-rows').childElementCount : 0;
})();