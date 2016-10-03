<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="jlab-row">
    <div class="jlab-cell-12 jlab-inner-table-caption">
        <span>${labels.get('inner.caption.address')}:</span>
    </div>
</div>

<div class="jlab-row">
    <div class="jlab-cell-6">

        <div class="jlab-form-item">
            <label for="edit-form-country">${labels.get('country')}</label>
            <input id="edit-form-country" type="text" name="country" value="${fullContact.country}"
                   placeholder="${labels.get('country.placeholder')}" data-validation-type="string30Chars">
        </div>

        <div class="jlab-form-item">
            <label for="edit-form-town">${labels.get('town')}</label>
            <input id="edit-form-town" type="text" name="town" value="${fullContact.town}"
                   placeholder="${labels.get('town.placeholder')}" data-validation-type="string30Chars">
        </div>

        <div class="jlab-form-item">
            <label for="edit-form-zip-code">${labels.get('zip')}</label>
            <input id="edit-form-zip-code" type="text" name="zipCode" value="${fullContact.zipCode}"
                   placeholder="${labels.get('zip.placeholder')}" data-validation-type="zip">
        </div>
    </div>
    <div class="jlab-cell-6">
        <div class="jlab-form-item">
            <label for="edit-form-street">${labels.get('street')}</label>
            <input id="edit-form-street" type="text" name="street" value="${fullContact.street}"
                   placeholder="${labels.get('street.placeholder')}" data-validation-type="string30Chars">
        </div>

        <div class="jlab-form-item">
            <label for="edit-form-house-number">${labels.get('house.number')}</label>
            <input id="edit-form-house-number" type="text" name="houseNumber" value="${fullContact.houseNumber}"
                   placeholder="${labels.get('house.number.placeholder')}" data-validation-type="numbers">
        </div>

        <div class="jlab-form-item">
            <label for="edit-form-flat-number">${labels.get('flat.number')}</label>
            <input id="edit-form-flat-number" type="text" name="flatNumber" value="${fullContact.flatNumber}"
                   placeholder="${labels.get('flat.number.placeholder')}" data-validation-type="numbers">
        </div>

    </div>
</div>
