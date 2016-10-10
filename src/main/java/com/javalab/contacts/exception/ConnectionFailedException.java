package com.javalab.contacts.exception;


public class ConnectionFailedException extends Exception {
    public ConnectionFailedException() {
        super();
    }

    public ConnectionFailedException(String message) {
        super(message);
    }
}
