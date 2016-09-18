<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="jlab-row">
    <div class="jlab-cell-12 jlab-inner-table-caption">
        <span>Address:</span>
    </div>
</div>

<div class="jlab-row">
    <div class="jlab-cell-6">

        <div class="jlab-form-item">
            <label for="edit-form-country">Country</label>
            <input id="edit-form-country" type="text" name="country" value="${fullContact.country}"
                   placeholder="country" data-validation-type="string">
        </div>

        <div class="jlab-form-item">
            <label for="edit-form-town">Town</label>
            <input id="edit-form-town" type="text" name="town" value="${fullContact.town}" placeholder="town"
                   data-validation-type="string">
        </div>

        <div class="jlab-form-item">
            <label for="edit-form-zip-code">Zip</label>
            <input id="edit-form-zip-code" type="text" name="zipCode" value="${fullContact.zipCode}"
                   placeholder="zipcode" data-validation-type="zip">
        </div>
    </div>
    <div class="jlab-cell-6">
        <div class="jlab-form-item">
            <label for="edit-form-street">Street</label>
            <input id="edit-form-street" type="text" name="street" value="${fullContact.street}"
                   placeholder="street" data-validation-type="string">
        </div>

        <div class="jlab-form-item">
            <label for="edit-form-house-number">House number</label>
            <input id="edit-form-house-number" type="text" name="houseNumber" value="${fullContact.houseNumber}"
                   placeholder="house number" data-validation-type="numbers">
        </div>

        <div class="jlab-form-item">
            <label for="edit-form-flat-number">Flat number</label>
            <input id="edit-form-flat-number" type="text" name="flatNumber" value="${fullContact.flatNumber}"
                   placeholder="house number" data-validation-type="numbers">
        </div>

    </div>
</div>
