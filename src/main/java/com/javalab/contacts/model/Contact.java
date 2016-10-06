package com.javalab.contacts.model;


import com.javalab.contacts.model.enumerations.MaritalStatus;
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
    private MaritalStatus maritalStatus;
    private String webSite;
    private String eMail;
    private String currentJob;
    private ContactAddress contactAddress;
    private Collection<ContactAttachment> attachments;
    private String photoLink;
    private String personalLink;
    private Collection<PhoneNumber> phoneNumbers;

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

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public ContactAddress getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(ContactAddress contactAddress) {
        this.contactAddress = contactAddress;
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

    public void setPersonalLink(String personalLink) {
        this.personalLink = personalLink;
    }

    public String getPersonalLink() {
        return personalLink;
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
        if (maritalStatus != contact.maritalStatus) return false;
        if (webSite != null ? !webSite.equals(contact.webSite) : contact.webSite != null) return false;
        if (eMail != null ? !eMail.equals(contact.eMail) : contact.eMail != null) return false;
        if (currentJob != null ? !currentJob.equals(contact.currentJob) : contact.currentJob != null) return false;
        if (contactAddress != null ? !contactAddress.equals(contact.contactAddress) : contact.contactAddress != null) return false;
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
                ", maritalStatus=" + maritalStatus +
                ", webSite='" + webSite + '\'' +
                ", eMail='" + eMail + '\'' +
                ", currentJob='" + currentJob + '\'' +
                ", contactAddress=" + contactAddress +
                ", attachments=" + attachments +
                ", photoLink='" + photoLink + '\'' +
                ", personalLink='" + personalLink + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}