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

    function submitHandler() {
        var formData = getPhoneNumberData();
        $('#phone-number-rows').innerHTML += buildPhoneRecord(phoneNumberCounter++, formData);
        toggleClass($('#modal-phone-edit'),'jlab-fade');
    }

    function buildPhoneRecord(counter, data) {
        return '<ul class="jlab-row">' +
            '<li class="jlab-cell-3">' +
            '<input type="checkbox" id="tempPhoneNumberId' + counter + '">' +
            '<input class="jlab-hidden" type="text" name="' + counter + '-phoneNumberId" value="">' +

            '<label for="tempPhoneNumberId' + counter + '"> +' + data.countryCode + ' (' + data.operatorCode + ') '
                                                                                       + data.phoneNumber + '</label>' +

            '<input class="jlab-hidden" type="text" name="' + counter + '-countryCode" value="'
                                                                                             + data.countryCode + '">' +

            '<input class="jlab-hidden" type="text" name="' + counter + '-operatorCode" value="'
                                                                                            + data.operatorCode + '">' +

            '<input class="jlab-hidden" type="text" name="' + counter + '-number" value="' + data.phoneNumber + '">' +
            '</li>' +

            '<li class="jlab-cell-2">' + data.phoneType +
            '<input class="jlab-hidden" type="text" name="' + counter + '-phoneType" value="' + data.phoneType + '">' +
            '</li>' +

            '<li class="jlab-cell-7">' + data.phoneComment +
            '<input class="jlab-hidden" type="text" name="' + counter + '-comment" value="$' + data.phoneComment + '">' +
            '</li>' +
            '</ul>'
    }

    $('#save-phoneNumber').addEventListener('click', submitHandler, false);
    var phoneNumberCounter = $('#phone-number-rows').childElementCount;
})();