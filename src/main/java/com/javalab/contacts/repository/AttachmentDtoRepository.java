package com.javalab.contacts.repository;


import com.javalab.contacts.dto.AttachmentDTO;

import java.util.Collection;

public interface AttachmentDtoRepository {

    void delete(Integer id);

    Collection<AttachmentDTO> getByContactId(Integer contactId);

    AttachmentDTO get(Integer id);
}
