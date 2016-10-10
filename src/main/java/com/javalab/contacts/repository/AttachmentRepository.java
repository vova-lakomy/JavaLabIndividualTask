package com.javalab.contacts.repository;


import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.exception.ConnectionFailedException;

public interface AttachmentRepository {

    void delete(Integer id) throws ConnectionFailedException;

    AttachmentDTO get(Integer id) throws ConnectionFailedException;
}
