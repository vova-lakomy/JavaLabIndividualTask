package com.javalab.contacts.model;


import java.time.LocalDate;

public class ContactAttachment {

    private Integer id;
    private String attachmentLink;
    private String attachmentComment;
    private LocalDate dateOfUpload;

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

    public LocalDate getDateOfUpload() {
        return dateOfUpload;
    }

    public void setDateOfUpload(LocalDate dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactAttachment that = (ContactAttachment) o;

        if (attachmentLink != null ? !attachmentLink.equals(that.attachmentLink) : that.attachmentLink != null)
            return false;
        if (attachmentComment != null ? !attachmentComment.equals(that.attachmentComment) : that.attachmentComment != null)
            return false;
        return dateOfUpload != null ? dateOfUpload.equals(that.dateOfUpload) : that.dateOfUpload == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ContactAttachment{" +
                "id=" + id +
                ", attachmentLink='" + attachmentLink + '\'' +
                ", attachmentComment='" + attachmentComment + '\'' +
                ", dateOfUpload=" + dateOfUpload +
                '}';
    }
}
