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
        if (isElementExist('#phone-numbers-empty-table')){
            $('#contact-edit-form').replaceChild(generatePhoneNumberEmptyList(),$('#phone-numbers-empty-table'))
        }
        var formData = getPhoneNumberData();
        $('#phone-number-rows').innerHTML += buildPhoneRecord(phoneNumberCounter++, formData);
        toggleClass($('#modal-phone-edit'),'jlab-fade');
    }

    function buildPhoneRecord(counter,data) {
        return '<ul class="jlab-row">' +
            '<li class="jlab-cell-3">' +
            '<input type="checkbox" id="tempPhoneNumberId' + counter + '">' +
            '<input class="jlab-hidden" type="text" name="phoneNumberId" value="">' +

            '<label for="tempPhoneNumberId' + counter + '"> +' + data.countryCode + ' (' + data.operatorCode + ') '
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



    function generatePhoneNumberEmptyList() {
        var list = document.createElement('div');
        list.className = "jlab-inner-table-container";
        list.id = "inner-phone-number-table";
        list.innerHTML =
            '<ul class="jlab-row jlab-inner-table-column-caption">'+
            '<li class="jlab-cell-3">Phone number</li>'+
            '<li class="jlab-cell-2">Type</li>'+
            '<li class="jlab-cell-7">Comment</li>'+
            '</ul>'+
            '<div id="phone-number-rows">'+
            '</div>'
        return list;
    }

    $('#save-phoneNumber').addEventListener('click', submitHandler, false);
    var phoneNumberCounter = (isElementExist('#phone-number-rows'))? $('#phone-number-rows').childElementCount : 0;
})();