(function () {
    var validations = {
        string : function (value) {
          return new RegExp('').test(value);
        },
        email : function (value) {
            return new RegExp('^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$').test(value);
        }

    };
    var forms = {};
    window.formValidation = function (event) {
        var formName = this.getAttribute('data-form-name');
        forms[formName] = forms[formName] || {};
        var currentForm = forms[formName];
        var changedInput = event.target;

        var validationType = changedInput.getAttribute('data-validation-type');

        if (validationType) {
            var validationMethod = validations[validationType];
            if (validationMethod){
                var isValid = validationMethod(changedInput.value);
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
            $('[type="submit"]').setAttribute('disabled','disabled');
        } else {
            $('[type="submit"]').removeAttribute('disabled');
        }
    }
})();
