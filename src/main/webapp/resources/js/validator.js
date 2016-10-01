(function () {

    var validations = {
        stringRequired : function (node) {
              return (new RegExp('^[^\x01-\x1F\x21-\x26\x28-\x2c\x2e-\x2f\x3a-\x40\x5b-\x60\x7b-\x7f]+$').test(node.value));
        },
        string : function (node) {
            return (new RegExp('^$|^[^\x01-\x1F\x21-\x26\x28-\x2c\x2e-\x2f\x3a-\x40\x5b-\x60\x7b-\x7f]+$').test(node.value));
        },
        email : function (node) {
            return new RegExp('^$|^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$').test(node.value);
        },
        url : function (node) {
            return new RegExp('^$|([\/\w \.-]*)*').test(node.value);
        },
        zip : function (node) {
            return new RegExp('^$|^[0-9]{5,6}$').test(node.value);
        },
        countryCode : function (node) {
            return new RegExp('^[0-9]{1,3}$').test(node.value);
        },
        operatorCode : function (node) {
            return new RegExp('^[0-9]{1,4}$').test(node.value);
        },
        phoneNumber : function (node) {
            return new RegExp('^[0-9]{3,9}$').test(node.value);
        },
        numbers : function (node) {
            return new RegExp('^$|^[0-9]{1,11}$').test(node.value);
        },
        date : function (node) {
            return checkDate(getDateFieldData(node));
        },
    };
    var forms = {};
    window.formValidation = function (event) {
        var formName = this.getAttribute('data-form-name');
        forms[formName] = forms[formName] || {};
        var currentForm = forms[formName];

        var changedInput = event.target;

        if (changedInput.getAttribute('data-date-part')){
            changedInput = changedInput.parentNode;
            validationType = 'date';
        } else {
            var validationType = changedInput.getAttribute('data-validation-type');
        }

        if (validationType) {
            var validationMethod = validations[validationType];
            if (validationMethod){
                var isValid = validationMethod(changedInput);
                    currentForm[changedInput.name] = isValid;
                if(!isValid){
                    addClass(changedInput, 'jlab-not-valid');
                } else {
                    removeClass(changedInput, 'jlab-not-valid');
                }
            }
        }

        var isFormValid = true;
        for (var key in currentForm){
            if (currentForm.hasOwnProperty(key) && currentForm[key] === false){
                isFormValid = false;
            }
        }
        if (!isFormValid){
            $('[data-form-name="' + formName +'"] [type="submit"]').setAttribute('disabled','disabled');
        } else {
            $('[data-form-name="' + formName +'"] [type="submit"]').removeAttribute('disabled');
        }
    };

    function getDateFieldData(dateNode) {
        var data = {};
        var children = dateNode.children;
        for(var i = 0; i < children.length; i++){
            data[children[i].getAttribute("data-date-part")] = children[i].value;
        }
        return data;
    }

    function checkDate(data) {
        var d = data.day;
        var m = data.month;
        var y = data.year;
        if (d && m && y){
            var endY = new Date().getFullYear();
            var startY = endY - 100;
            if (y >= startY && y <= endY){
                if (m >= 1 && m <= 12){
                    if (d >= 1 && d <= 28){
                        return true;
                    } else if (d == 29 && ((y % 4) === 0)){
                        return true;
                    } else if ((d == 29 || d == 30) && m != 2){
                        return true;
                    } else if (d == 31 && (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12)){
                        return true;
                    } else {
                        return false;
                    }
                } else return false;
            } else return false;
        } else return false;
    }

})();
