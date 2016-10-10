<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="jlab-row">
    <div class="jlab-cell-12">
        <div class="jlab-button-block jlab-pull-right">
            <a class="jlab-button"
               href="edit">${labels.get('new.contact')}</a>

            <a class="jlab-button"
               href="search"
               target="_self">${labels.get('search')}</a>

            <button id="send-email"
                    class="jlab-button"
                    type="submit"
                    formaction="mail">${labels.get('send.email')}</button>

            <button id="delete-contact"
                    class="jlab-button"
                    type="submit"
                    formaction="deleteContact"
                    disabled>${labels.get('delete')}</button>
        </div>
    </div>
</div>


