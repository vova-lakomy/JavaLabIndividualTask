function hide(selector) {
    var elem = $(selector);
    if (elem) {
        setCSS(elem, 'display', 'none');
    }
}

function show(selector) {
    var elem = document.querySelector(selector);
    if (elem) {
        setCSS(elem, 'display', '');
    }
}

function setCSS(elem, key, value) {
    elem.style[key] = value;
}

function hasClass(element, className) {
    var classes = getClasses(element);
    return classes.indexOf(className) > 0;
}

function toggleClass(element, className) {
    if (hasClass(element, className)) {
        removeClass(element, className);
    } else {
        addClass(element, className);
    }
}

function getClasses(element) {
    return (element.getAttribute('class') || '').split(' ');
}

function addClass(element, className) {
    var classes = getClasses(element);
    classes.push(className);
    classes = classes.join(' ');
    element.setAttribute(
        'class',
        classes
    );
}

function removeClass(element, className) {
    element.setAttribute(
        'class',
        (element.getAttribute('class') || '').replace(new RegExp('\\b' + className + '\\b', 'g'), '')
    );
}

function $(selector) {
    return document.querySelector(selector);
}
