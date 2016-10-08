package com.javalab.contacts.exception;


public class ContactNotFoundException extends Exception {
    public ContactNotFoundException() {
        super();
    }

    public ContactNotFoundException(String message) {
        super(message);
    }
}
