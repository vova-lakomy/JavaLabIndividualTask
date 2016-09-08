package com.javalab.contacts.model;


import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.Sex;

import java.time.LocalDate;
import java.util.Collection;

public class Contact {

    private Integer id;

    private String firstName;

    private String secondName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Sex sex;

    private String nationality;

    private MartialStatus martialStatus;

    private String webSite;

    private String eMail;

    private String currentJob;

    private Collection<ContactAddress> contactAddresses;

    private Collection<ContactAttachment> attachments;

    private String photoLink;

    private Collection <PhoneNumber> phoneNumbers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public MartialStatus getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(MartialStatus martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public Collection<ContactAddress> getContactAddresses() {
        return contactAddresses;
    }

    public void setContactAddresses(Collection<ContactAddress> contactAddresses) {
        this.contactAddresses = contactAddresses;
    }

    public Collection<ContactAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<ContactAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Contact(Integer id, String firstName, String secondName, String lastName, LocalDate dateOfBirth, Sex sex,
                   String nationality, MartialStatus martialStatus, String webSite, String eMail, String currentJob,
                   Collection<ContactAddress> contactAddresses, Collection<ContactAttachment> attachments,
                   String photoLink, Collection<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.nationality = nationality;
        this.martialStatus = martialStatus;
        this.webSite = webSite;
        this.eMail = eMail;
        this.currentJob = currentJob;
        this.contactAddresses = contactAddresses;
        this.attachments = attachments;
        this.photoLink = photoLink;
        this.phoneNumbers = phoneNumbers;
    }

    public Contact() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null) return false;
        if (secondName != null ? !secondName.equals(contact.secondName) : contact.secondName != null) return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(contact.dateOfBirth) : contact.dateOfBirth != null) return false;
        if (sex != contact.sex) return false;
        if (nationality != null ? !nationality.equals(contact.nationality) : contact.nationality != null) return false;
        if (martialStatus != contact.martialStatus) return false;
        if (webSite != null ? !webSite.equals(contact.webSite) : contact.webSite != null) return false;
        if (eMail != null ? !eMail.equals(contact.eMail) : contact.eMail != null) return false;
        if (currentJob != null ? !currentJob.equals(contact.currentJob) : contact.currentJob != null) return false;
        if (contactAddresses != null ? !(contactAddresses.size()
                == (contact.contactAddresses.size())) : contact.contactAddresses != null) return false;
        if (attachments != null ? !(attachments.size()
                == (contact.attachments.size())) : contact.attachments != null) return false;
        if (photoLink != null ? !photoLink.equals(contact.photoLink) : contact.photoLink != null) return false;
        return (phoneNumbers != null ? (phoneNumbers.size() == (contact.phoneNumbers.size())) : contact.phoneNumbers != null);

    }

    @Override
    public int hashCode() {
        return firstName != null ? firstName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", sex=" + sex +
                ", nationality='" + nationality + '\'' +
                ", martialStatus=" + martialStatus +
                ", webSite='" + webSite + '\'' +
                ", eMail='" + eMail + '\'' +
                ", currentJob='" + currentJob + '\'' +
                ", contactAddresses=" + contactAddresses +
                ", attachments=" + attachments +
                ", photoLink='" + photoLink + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}
