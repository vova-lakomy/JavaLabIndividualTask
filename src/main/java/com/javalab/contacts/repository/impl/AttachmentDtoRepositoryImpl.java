package com.javalab.contacts.repository.impl;

import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.repository.AttachmentDtoRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;


public class AttachmentDtoRepositoryImpl implements AttachmentDtoRepository {
    @Override
    public void delete(Integer id) {

    }

    @Override
    public Collection<AttachmentDTO> getAttachments(Integer contactId) {
        if (contactId != null) {
            Collection<AttachmentDTO> attachmentDTOs = new ArrayList<>();
            new JdbcContactAttachmentDao().getByContactId(contactId).forEach(attachment -> {
                Integer id = attachment.getId();
                String fileName = attachment.getAttachmentLink()
                        .substring(attachment.getAttachmentLink().lastIndexOf("/") + 1);
                String uploadDate = attachment.getDateOfUpload().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                String comment = attachment.getAttachmentComment();
                String attachmentLink = attachment.getAttachmentLink();

                attachmentDTOs.add(new AttachmentDTO(id, fileName, uploadDate, comment, attachmentLink));
            });
            return attachmentDTOs.size() > 0 ? attachmentDTOs : null;
        } else {
            return null;
        }
    }
}
