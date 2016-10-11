package com.javalab.contacts.exception;


public class SendingMailException extends Exception{
    public SendingMailException() {
        super();
    }

    public SendingMailException(String s) {
        super(s);
    }
}
