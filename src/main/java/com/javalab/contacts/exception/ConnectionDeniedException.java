package com.javalab.contacts.exception;


public class ConnectionDeniedException extends Exception {
    public ConnectionDeniedException() {
        super();
    }

    public ConnectionDeniedException(String message) {
        super(message);
    }
}
