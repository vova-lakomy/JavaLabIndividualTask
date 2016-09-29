package com.javalab.contacts.dto;


import java.util.Collection;

public class ContactFullDTO {

    private Integer id;
    private String firstName;
    private String secondName;
    private String lastName;
    private Integer dayOfBirth;
    private Integer monthOfBirth;
    private Integer yearOfBirth;
    private String sex;
    private String nationality;
    private String martialStatus;
    private String webSite;
    private String eMail;
    private String currentJob;
    private String country;
    private String town;
    private String street;
    private Integer houseNumber;
    private Integer flatNumber;
    private Integer zipCode;
    private String photoLink;
    private Collection<PhoneNumberDTO> phoneNumbers;
    private Collection<AttachmentDTO> attachments;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getDayOfBirth() {
        return dayOfBirth;
    }

    public Integer getMonthOfBirth() {
        return monthOfBirth;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public String getNationality() {
        return nationality;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public String getWebSite() {
        return webSite;
    }

    public String geteMail() {
        return eMail;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public Integer getFlatNumber() {
        return flatNumber;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public Collection<PhoneNumberDTO> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Collection<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public ContactFullDTO() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDayOfBirth(Integer dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public void setMonthOfBirth(Integer monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public void setPhoneNumbers(Collection<PhoneNumberDTO> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setAttachments(Collection<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }
}
