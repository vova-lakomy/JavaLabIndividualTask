package com.javalab.contacts.model;


public class ContactAttachment {

    private Integer id;

    private String attachmentLink;

    private String attachmentComment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttachmentLink() {
        return attachmentLink;
    }

    public void setAttachmentLink(String attachmentLink) {
        this.attachmentLink = attachmentLink;
    }

    public String getAttachmentComment() {
        return attachmentComment;
    }

    public void setAttachmentComment(String attachmentComment) {
        this.attachmentComment = attachmentComment;
    }
}
