package com.javalab.contacts.repository;


import com.javalab.contacts.exception.ConnectionFailedException;

public interface PhoneRepository {

    void delete(Integer id) throws ConnectionFailedException;
}
