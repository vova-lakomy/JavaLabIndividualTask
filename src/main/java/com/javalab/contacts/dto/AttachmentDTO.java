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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAttachmentLink(String attachmentLink) {
        this.attachmentLink = attachmentLink;
    }
}
