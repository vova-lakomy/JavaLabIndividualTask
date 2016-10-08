package com.javalab.contacts.repository;


import com.javalab.contacts.exception.ConnectionDeniedException;

public interface PhoneRepository {

    void delete(Integer id) throws ConnectionDeniedException;
}
