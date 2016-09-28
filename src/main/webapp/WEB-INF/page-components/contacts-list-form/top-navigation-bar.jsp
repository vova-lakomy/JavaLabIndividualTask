<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="jlab-row">
    <div class="jlab-cell-12">
        <div class="jlab-button-block jlab-pull-right">
            <a class="jlab-button" href="edit">${labels.get('new.contact')}</a>
            <button class="jlab-button" type="submit" formaction="deleteContact">${labels.get('delete')}</button>
            <a class="jlab-button" href="search" target="_self">${labels.get('search')}</a>
            <button class="jlab-button" type="submit" formaction="mail">${labels.get('send.email')}</button>
        </div>
    </div>
</div>


