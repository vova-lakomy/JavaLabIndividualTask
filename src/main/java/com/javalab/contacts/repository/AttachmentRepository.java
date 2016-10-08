package com.javalab.contacts.repository;


import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.exception.ConnectionDeniedException;

import java.util.Collection;

public interface AttachmentRepository {

    void delete(Integer id) throws ConnectionDeniedException;

    AttachmentDTO get(Integer id) throws ConnectionDeniedException;
}
