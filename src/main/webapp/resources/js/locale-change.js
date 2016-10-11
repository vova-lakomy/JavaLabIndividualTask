(function () {

    function makeLocaleSelected(element) {
        if (!hasClass(element,'jlab-selected-locale')){
            addClass(element,'jlab-selected-locale');
        }
    }

    function makeLocaleUnselected(element) {
        if (hasClass(element,'jlab-selected-locale')){
            removeClass(element,'jlab-selected-locale');
        }
    }

    function enLocaleHandler(event) {
        event.target.parentNode.action = '';
        event.target.parentNode.submit();
    }

    function ruLocaleHandler(event) {
        event.target.parentNode.submit();
    }

})();
