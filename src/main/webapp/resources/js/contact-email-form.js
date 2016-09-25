(function () {

    function setMessageText(){
        var selectedTemplate = $('#templateSelect').value;
        if (selectedTemplate){
            $('#mailText').value = $('#' + selectedTemplate).value;
        } else {
            $('#mailText').value = '';
        }

    }

    $('#templateSelect').addEventListener('change',setMessageText, false);
    // validation listeners

    $('#email-form').addEventListener('keyup', formValidation, false);
    $('#email-form').addEventListener('input', formValidation, false);
})();