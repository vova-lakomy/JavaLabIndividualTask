package com.javalab.contacts.dto;


public class ContactFullDTO {

    private Integer id;

    private String firstName;

    private String secondName;

    private String lastName;

    private String dateOfBirth;

    private String sex;

    private String nationality;

    private String martialStatus;

    private String webSite;

    private String eMail;

    private String currentJob;

    private String photoLink;

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

    public String getDateOfBirth() {
        return dateOfBirth;
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

    public String getPhotoLink() {
        return photoLink;
    }

    public ContactFullDTO(Integer id, String firstName, String secondName, String lastName, String dateOfBirth,
                          String sex, String nationality, String martialStatus, String webSite, String eMail,
                          String currentJob, String photoLink) {
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
        this.photoLink = photoLink;
    }
}
