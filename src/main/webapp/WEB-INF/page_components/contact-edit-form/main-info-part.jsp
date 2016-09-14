<div class="jlab-row">
    <div class="jlab-cell-12">


        <div class="jlab-row">

            <div class="jlab-cell-6">
                <div class="jlab-form-item">
                    <input class="jlab-hidden" name="contactId" value="${fullContact.contactId}">
                    <label for="edit-form-first-name">First name</label>
                    <input id="edit-form-first-name" type="text" name="firstName" value="${fullContact.firstName}"
                           placeholder="first name">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-last-name">Last name</label>
                    <input id="edit-form-last-name" type="text" name="lastName" value="${fullContact.lastName}"
                           placeholder="last name">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-second-name">Second name</label>
                    <input id="edit-form-second-name" type="text" name="secondtName" value="${fullContact.secondName}"
                           placeholder="second name">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-birth-day">Birth date</label>
                    <div class="jlab-date-field">
                        <input id="edit-form-birth-day" type="text" placeholder="day" title="day" name="dayOfBirth"
                               value="${fullContact.dayOfBirth}">
                        <input id="edit-form-birth-month" type="text" placeholder="month" title="month" name="monthOfBirth"
                               value="${fullContact.monthOfBirth}">
                        <input id="edit-form-birth-year" type="text" placeholder="year" title="year" name="yearOfBirth"
                               value="${fullContact.yearOfBirth}">
                    </div>
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-sex">Sex</label>
                    <select id="edit-form-sex" name="sex">
                        <option>male</option>
                        <option>female</option>
                    </select>
                </div>

            </div>


            <div class="jlab-cell-6">
                <div class="jlab-form-item">
                    <label for="edit-form-nationality">Nationality</label>
                    <input id="edit-form-nationality" type="text" name="nationality" value="${fullContact.nationality}"
                           placeholder="nationality">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-martial-status">Martial status</label>
                    <input id="edit-form-martial-status" type="text" name="martialStatus"
                           value="${fullContact.martialStatus}"
                           placeholder="martial status">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-web-site">Web site</label>
                    <input id="edit-form-web-site" type="text" name="webSite" value="${fullContact.webSite}"
                           placeholder="web site">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-email">e-mail</label>
                    <input id="edit-form-email" type="text" name="eMail" value="${fullContact.eMail}"
                           placeholder="e-mail">
                </div>

                <div class="jlab-form-item">
                    <label for="edit-form-company">Company</label>
                    <input id="edit-form-company" type="text" name="company" value="${fullContact.currentJob}"
                           placeholder="company">
                </div>


            </div>

        </div>
    </div>
</div>
