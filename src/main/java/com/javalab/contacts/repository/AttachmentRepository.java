package com.javalab.contacts.repository;


import com.javalab.contacts.dto.AttachmentDTO;

import java.util.Collection;

public interface AttachmentRepository {

    void delete(Integer id);

    AttachmentDTO get(Integer id);
}
