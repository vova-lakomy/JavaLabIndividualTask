(function () {

    var validations = {
        stringRequired20Chars : function (node) {
              return (new RegExp('^[^\x01-\x1F\x21-\x26\x28-\x2c\x3a-\x40\x5b-\x60\x7b-\x7f\/]{1,20}$').test(node.value));
        },
        string20Chars : function (node) {
            return (new RegExp('^$|^[^\x01-\x1F\x21-\x26\x28-\x2c\x3a-\x40\x5b-\x60\x7b-\x7f\/]{1,20}$').test(node.value));
        },
        string30Chars : function (node) {
            return (new RegExp('^$|^[^\x01-\x1F\x21-\x26\x28-\x2c\x3a-\x40\x5b-\x60\x7b-\x7f\/]{1,30}$').test(node.value));
        },
        string50Chars : function (node) {
            return (new RegExp('^$|^[^\x01-\x1F\x21-\x26\x28-\x2c\x3a-\x40\x5b-\x60\x7b-\x7f\/]{1,50}$').test(node.value));
        },
        string100Chars : function (node) {
            return (new RegExp('^$|^[^\x01-\x1F\x21-\x26\x28-\x2c\x3a-\x40\x5b-\x60\x7b-\x7f\/]{1,100}$').test(node.value));
        },
        string150Chars : function (node) {
            return (new RegExp('^$|^[^\x01-\x1F\x21-\x26\x28-\x2c\x3a-\x40\x5b-\x60\x7b-\x7f\/]{1,150}$').test(node.value));
        },
        string200Chars : function (node) {
            return (new RegExp('^$|^[^\x01-\x1F\x21-\x26\x28-\x2c\x3a-\x40\x5b-\x60\x7b-\x7f\/]{1,200}$').test(node.value));
        },
        email : function (node) {
            return new RegExp('^$|^[\\w-.]{1,40}@[\\w]+\.[\\w]{2,4}$').test(node.value);
        },
        comment : function (node) {
            return node.value.length <= 1000;
        },
        url : function (node) {
            return new RegExp('^$|^[\/\\w\.-:&=]{1,100}$').test(node.value);
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
            return new RegExp('^$|^[0-9]{1,10}$').test(node.value);
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
        var day = data.day;
        var month = data.month;
        var year = data.year;
        return (isDateValid(day, month, year) && !isDateFuture(day, month, year))
    }

    function isDateFuture(day, month, year) {
        var dateNow = new Date();
        var dateToCheck = new Date(year + '-' + month + '-' + day);
        return dateToCheck > dateNow;
    }

    function isDateValid(d, m, y) {
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
        } else if (!d && !m && !y){
            return true;
        } else if (!d && !m && y){
            return  true;
        } else return false;
    }

})();
