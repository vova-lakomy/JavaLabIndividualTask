package com.javalab.contacts.dto;


public class AttachmentDTO {

    private Integer id;

    private String fileName;

    private String uploadDate;

    private String comment;

    private String attachmentLink;

    public Integer getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getComment() {
        return comment;
    }

    public String getAttachmentLink() {
        return attachmentLink;
    }

    public AttachmentDTO(Integer id, String fileName, String uploadDate, String comment, String attachmentLink) {
        this.id = id;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.comment = comment;
        this.attachmentLink = attachmentLink;
    }
}
