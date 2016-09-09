<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="page_components/header.jsp"/>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-row">
            <div class="jlab-cell-12">
                <div class="jlab-main-photo-container">

                </div>
            </div>
        </div>

        <form action="javascript: alert('hello')">

            <div class="jlab-row">
                <div class="jlab-cell-12">


                    <div class="jlab-row">

                        <div class="jlab-cell-6">
                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--first name--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-first-name">First name</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-first-name" type="text" name="firstName" value=""
                                           placeholder="first name">
                                </div>
                                <%--/first name--%>
                            </div>

                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--last name--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-last-name">Last name</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-last-name" type="text" name="lastName" value=""
                                           placeholder="last name">
                                </div>
                                <%--/last name--%>
                            </div>

                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--second name--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-second-name">Second name</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-second-name" type="text" name="secondtName" value=""
                                           placeholder="second name">
                                </div>
                                <%--/second name--%>
                            </div>

                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--birth date--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-birth-date">Birth date</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-birth-date" type="date" name="birthDate" value="">
                                </div>
                                <%--/birth date--%>
                            </div>

                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--sex--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-sex">Sex</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <select id="edit-form-sex" type="" name="sex">
                                        <option>Male</option>
                                        <option>Female</option>
                                    </select>
                                </div>
                                <%--/sex--%>
                            </div>

                        </div>


                        <div class="jlab-cell-6">
                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--nationality--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-nationality">Nationality</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-nationality" type="text" name="nationality"
                                           placeholder="nationality">
                                </div>
                                <%--/nationality--%>
                            </div>

                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--martial status--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-martial-status">Martial status</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-martial-status" type="text" name="martialStatus" value=""
                                           placeholder="martial status">
                                </div>
                                <%--/martial status--%>
                            </div>

                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--web-site--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-web-site">Web site</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-web-site" type="text" name="webSite" value=""
                                           placeholder="web site">
                                </div>
                                <%--/web-site--%>
                            </div>

                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--e-mail--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-email">e-mail</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-email" type="text" name="eMail" value="" placeholder="e-mail">
                                </div>
                                <%--/e-mail--%>
                            </div>

                            <div class="jlab-row jlab-vertical-padding-10">
                                <%--company--%>
                                <div class="jlab-cell-3">
                                    <label for="edit-form-company">Company</label>
                                </div>
                                <div class="jlab-cell-9">
                                    <input id="edit-form-company" type="text" name="company" value=""
                                           placeholder="company">
                                </div>
                                <%--/company--%>
                            </div>


                        </div>

                    </div>
                </div>
            </div>
            <div class="jlab-row jlab-vertical-padding-10">
                <div class="jlab-cell-12">
                    Address:
                </div>
            </div>


            <div class="jlab-row jlab-vertical-padding-10">

                <div class="jlab-cell-6">


                    <div class="jlab-row jlab-vertical-padding-10">
                        <%--country--%>
                        <div class="jlab-cell-3">
                            <label for="edit-form-country">Country</label>
                        </div>
                        <div class="jlab-cell-9">
                            <input id="edit-form-country" type="text" name="country" value="" placeholder="country">
                        </div>
                        <%--/country--%>
                    </div>

                    <div class="jlab-row jlab-vertical-padding-10">
                        <%--town--%>
                        <div class="jlab-cell-3">
                            <label for="edit-form-town">Town</label>
                        </div>
                        <div class="jlab-cell-9">
                            <input id="edit-form-town" type="text" name="town" value="" placeholder="town">
                        </div>
                        <%--/town--%>
                    </div>

                    <div class="jlab-row jlab-vertical-padding-10">
                        <%--zipcode--%>
                        <div class="jlab-cell-3">
                            <label for="edit-form-zip-code">Zip</label>
                        </div>
                        <div class="jlab-cell-9">
                            <input id="edit-form-zip-code" type="text" name="zipCode" value="" placeholder="zipcode">
                        </div>
                        <%--/zipcode--%>
                    </div>
                </div>
                <div class="jlab-cell-6">
                    <div class="jlab-row jlab-vertical-padding-10">
                        <%--street--%>
                        <div class="jlab-cell-3">
                            <label for="edit-form-street">Street</label>
                        </div>
                        <div class="jlab-cell-9">
                            <input id="edit-form-street" type="text" name="street" value=""
                                   placeholder="street">
                        </div>
                        <%--/street--%>
                    </div>

                    <div class="jlab-row jlab-vertical-padding-10">
                        <%--house number--%>
                        <div class="jlab-cell-3">
                            <label for="edit-form-house-number">House number</label>
                        </div>
                        <div class="jlab-cell-9">
                            <input id="edit-form-house-number" type="text" name="houseNumber" value=""
                                   placeholder="house number">
                        </div>
                        <%--/house number--%>
                    </div>

                    <div class="jlab-row jlab-vertical-padding-10">
                        <%--flat number--%>
                        <div class="jlab-cell-3">
                            <label for="edit-form-flat-number">Flat number</label>
                        </div>
                        <div class="jlab-cell-9">
                            <input id="edit-form-flat-number" type="text" name="flatNumber" value=""
                                   placeholder="house number">
                        </div>
                        <%--/flat number--%>
                    </div>

                </div>

            </div>

            <%--submit--%>
            <div class="jlab-row jlab-vertical-padding-10">
                <div class="jlab-cell-12">
                    <button type="submit" class="jlab-button">submit</button>
                </div>
            </div>
            <%--/submit--%>

        </form>
    </div>
</div>


<jsp:include page="page_components/footer.jsp"/>