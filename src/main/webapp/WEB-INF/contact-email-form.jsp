<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="jlab-main-content-container">
    <div class="jlab-main-content">
        <div class="jlab-form-container">
            <h4>Send email</h4>
            <form action="alert('send e-mail')">
                <div class="jlab-form-item">
                    <label>To:</label>
                </div>
                <div class="jlab-form-item">
                    <label for="mailTopic">Topic :</label>
                    <input id="mailTopic" type="text" placeholder="topic" name="mailTopic">
                </div>

                <textarea id="mailText" name="mailText"  placeholder="select template or type text here"></textarea>
                <div class="jlab-button-block jlab-vertical-padding-10 jlab-pull-right">
                    <button type="button" class="jlab-button">cancel</button>
                    <button type="submit" class="jlab-button">send</button>
                </div>
            </form>
        </div>
    </div>
</div>
