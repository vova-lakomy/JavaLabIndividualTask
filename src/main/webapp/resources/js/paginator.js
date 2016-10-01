(function () {

    function togglePageSelectionInput(){
        toggleClass($('#page-selector-go-button'), 'jlab-hidden');
        toggleClass($('#page-selector-input'), 'jlab-hidden');
        toggleClass($('#jlab-page-selector'), 'jlab-hidden');
    }

    function changePageHrefValue() {
        var href = $('#page-selector-go-button').href;
        var partToReplace = href.substring(href.indexOf('page='));
        var newPageNumber = $('#page-selector-input').value;
        href = href.replace(partToReplace,'page=' + newPageNumber);
        $('#page-selector-go-button').href = href;
    }

    $('#jlab-page-selector').addEventListener('click', togglePageSelectionInput, false);
    $('#page-selector-input').addEventListener('input', changePageHrefValue, false);
    $('#page-selector-input').addEventListener('keyup', changePageHrefValue, false);
})();

