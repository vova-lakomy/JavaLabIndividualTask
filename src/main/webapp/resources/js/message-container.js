(function () {

    function handleMessageShow() {
        if ($('#should-show-message').value === 'true'){
            fadeInMessage();
        }
    }

    function handleErrorMessageShow() {
        if ($('#should-show-error-message').value === 'true'){
            fadeInErrorMessage();
        }
    }

    function fadeInMessage() {
        toggleClass($('#message-overlay'), 'jlab-fade');
        window.setTimeout(fadeOutMessage, 1600);
    }

    function fadeInErrorMessage() {
        toggleClass($('#error-message-overlay'), 'jlab-fade');
    }

    function fadeOutMessage() {
        toggleClass($('#message-overlay'), 'jlab-fade');
    }

    function closeMessagePopup(){
        toggleClass($('#message-overlay'), 'jlab-hidden');
    }

    function closeErrorMessagePopup(){
        toggleClass($('#error-message-overlay'), 'jlab-hidden');
    }

    function handleMouseEnter(e){
        e.target.parentNode.style.transition = 'opacity, 20s';
    }
    function handleMouseLeave(e){
        e.target.parentNode.style.removeProperty('transition');
    }

//listeners
    $('#close-message').addEventListener('click', closeMessagePopup, false);
    $('#close-error-message').addEventListener('click', closeErrorMessagePopup, false);
    document.addEventListener("DOMContentLoaded", handleMessageShow);
    document.addEventListener("DOMContentLoaded", handleErrorMessageShow);

    $('#message-container').addEventListener('mouseenter', handleMouseEnter, false);
    $('#message-container').addEventListener('mouseleave', handleMouseLeave, false);
})();