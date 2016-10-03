(function () {

    function handleMessageShow() {
        if ($('#should-show-message').value === 'true'){
            fadeInMessage();
        }
    }

    function fadeInMessage() {
        toggleClass($('#message-overlay'), 'jlab-fade');
        window.setTimeout(fadeOutMessage, 1600);
    }

    function fadeOutMessage() {
        toggleClass($('#message-overlay'), 'jlab-fade');
    }

    function closeMessagePopup(){
        toggleClass($('#message-overlay'), 'jlab-hidden');
    }

    $('#close-message').addEventListener('click', closeMessagePopup, false);
    document.addEventListener("DOMContentLoaded", handleMessageShow);
})();