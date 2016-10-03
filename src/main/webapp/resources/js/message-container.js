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

    function handleMouseEnter(e){
        e.target.parentNode.style.transition = 'opacity, 20s';
    }
    function handleMouseLeave(e){
        e.target.parentNode.style.removeProperty('transition');
    }

//listeners
    $('#close-message').addEventListener('click', closeMessagePopup, false);
    document.addEventListener("DOMContentLoaded", handleMessageShow);

    $('#message-container').addEventListener('mouseenter', handleMouseEnter, false);
    $('#message-container').addEventListener('mouseleave', handleMouseLeave, false);
})();