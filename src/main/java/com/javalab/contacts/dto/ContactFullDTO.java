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

    private int houseNumber;

    private int flatNumber;

    private int zipCode;

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

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public int getZipCode() {
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

    public ContactFullDTO(Integer id, String firstName, String secondName, String lastName, Integer dayOfBirth,
                          Integer monthOfBirth, Integer yearOfBirth, String sex, String nationality,
                          String martialStatus, String webSite, String eMail, String currentJob, String country,
                          String town, String street, int houseNumber, int flatNumber, int zipCode, String photoLink,
                          Collection<PhoneNumberDTO> phoneNumbers, Collection<AttachmentDTO> attachments) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
        this.sex = sex;
        this.nationality = nationality;
        this.martialStatus = martialStatus;
        this.webSite = webSite;
        this.eMail = eMail;
        this.currentJob = currentJob;
        this.country = country;
        this.town = town;
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
        this.zipCode = zipCode;
        this.photoLink = photoLink;
        this.phoneNumbers = phoneNumbers;
        this.attachments = attachments;
    }
}
