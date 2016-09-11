<div id="modal-phone-edit" class="jlab-modal-container jlab-hidden">
    <form class="jlab-modal jlab-phone-number-edit-form">
        <b>Edit a phone</b>
        <div class="jlab-form-item">
            <label for="phone-number-country-code">Country code</label>
            <input id="phone-number-country-code" name="countryCode" type="text" value="" placeholder="country code">
        </div>
        <div class="jlab-form-item">
            <label for="phone-number-operator-code">Operator code</label>
            <input id="phone-number-operator-code" name="operatorCode" type="text" value="" placeholder="operator code">
        </div>
        <div class="jlab-form-item">
            <label for="phone-number">Phone number</label>
            <input id="phone-number" name="phoneNumber" type="text" value="" placeholder="phone number">
        </div>
        <div class="jlab-form-item">
            <label for="phone-type-select">Phone type</label>
            <select id="phone-type-select" name="phoneType">
                <option>mobile</option>
                <option>home</option>
            </select>
        </div>

        <div class="jlab-form-item">
            <label for="phone-number-comment">Comment</label>
            <input id="phone-number-comment" type="text" value="" placeholder="comment">
        </div>
        <div class="jlab-form-item">
            <button type="button" class="jlab-button">save</button>
            <button type="button" class="jlab-button"
                    onclick="javascript: toggleClass($('#modal-phone-edit'),'jlab-hidden')">cancel</button>
        </div>
    </form>
</div>
