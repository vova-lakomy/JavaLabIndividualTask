(function () {

    function handleListFormClick(e){
        if (hasClass(e.target,'jlab-row')){
            alert("row!!!");
        }
        var checkboxes = $all('[data-is-checkbox="true"]:checked');
        if (checkboxes.length > 0){
            enableMenuButtons();
        } else {
            disableMenuButtons();
        }
    }

    function disableMenuButtons() {
        $('#send-email').setAttribute('disabled', 'disabled');
        $('#delete-contact').setAttribute('disabled', 'disabled');
    }

    function enableMenuButtons() {
        $('#send-email').removeAttribute('disabled');
        $('#delete-contact').removeAttribute('disabled');
    }

// listeners
    $('#contact-list-form').addEventListener('click', handleListFormClick, false);
})();
