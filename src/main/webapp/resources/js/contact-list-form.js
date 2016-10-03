(function () {

    function handleListFormClick(e){
        var checkboxes = $all('[data-is-checkbox="true"]:checked');
        if (checkboxes.length > 0){
            enableMenuButtons();
        } else {
            disableMenuButtons();
        }
    }

    function handleContactRowClick(e){
        var contactRow = e.target.parentNode.parentNode;
        if (contactRow.getAttribute('data-contact-row') === 'true'){
            toggleCheckedState(contactRow.children[0]);
        }
    }

    function toggleCheckedState(element) {
        var elementType = element.getAttribute('type').toLowerCase();
        if (elementType === 'checkbox'){
            if (element.checked){
                element.parentNode.parentNode.style.removeProperty('background-color');
                element.parentNode.parentNode.style.removeProperty('outline');
                element.checked = false;
            } else {
                setCSS(element.parentNode.parentNode,'background-color','#c1c1c1');
                setCSS(element.parentNode.parentNode,'outline','1px solid grey');
                element.checked = true;
            }
        }
    }

    function disableMenuButtons() {
        // $('#send-email').setAttribute('disabled', 'disabled');
        $('#delete-contact').setAttribute('disabled', 'disabled');
    }

    function enableMenuButtons() {
        // $('#send-email').removeAttribute('disabled');
        $('#delete-contact').removeAttribute('disabled');
    }

// listeners
    $('#contact-list-form').addEventListener('click', handleListFormClick, false);
    var contactRows = $all('[data-contact-row="true"]');
    for (var i = 0; i < contactRows.length; i++){
        contactRows[i].addEventListener('click', handleContactRowClick, false);
    }
})();
