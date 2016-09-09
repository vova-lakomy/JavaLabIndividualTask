<li>
    <div class="jlab-row ">
        <%--<div class="jlab-cell-1">--%>
            <jsp:element name="input">
                <jsp:attribute name="type">checkbox</jsp:attribute>
                <jsp:attribute name="id">${contact.id}</jsp:attribute>
            </jsp:element>

        <%--</div>--%>
        <div class="jlab-cell-4 " onclick="javascript: alert('clickable')">
            <div class="jlab-contact-full-name jlab-horizontal-padding-10 jlab-clickable-contact">
                ${contact.fullName}
            </div>
        </div>
        <div class="jlab-cell-2">
            <div class="jlab-horizontal-padding-10">
                ${contact.dateOfBirth}
            </div>
        </div>
        <div class="jlab-cell-4">
            <div class="jlab-horizontal-padding-10">
                ${contact.addresses[0]}
            </div>
        </div>
        <div class="jlab-cell-2">
            <div class="jlab-horizontal-padding-10">
                ${contact.company}
            </div>
        </div>
    </div>
</li>
