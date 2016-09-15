<li class="jlab-selected-contact">
    <div class="jlab-row ">
        <input type="checkbox" name="selectedId" value="${contact.id}">

            <%--<jsp:element name="input">--%>
                <%--<jsp:attribute name="type">checkbox</jsp:attribute>--%>
                <%--<jsp:attribute name="id">${contact.id}</jsp:attribute>--%>
            <%--</jsp:element>--%>

        <div class="jlab-cell-4 ">
            <div class="jlab-contact-full-name jlab-horizontal-padding-10">
                <a href="edit?contactId=${contact.id}" >${contact.fullName}</a>
            </div>
        </div>
        <div class="jlab-cell-2">
            <div class="jlab-horizontal-padding-10">
                ${contact.dateOfBirth}
            </div>
        </div>
        <div class="jlab-cell-4">
            <div class="jlab-horizontal-padding-10">
                ${contact.address}
            </div>
        </div>
        <div class="jlab-cell-2">
            <div class="jlab-horizontal-padding-10">
                ${contact.company}
            </div>
        </div>
    </div>
</li>
