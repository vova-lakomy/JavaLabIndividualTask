package com.javalab.contacts.dto;


public class ContactShortDTO {

    private Integer id;

    private String fullName;

    private String dateOfBirth;

    private String address;

    private String company;

    private String eMail;

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getCompany() {
        return company;
    }

    public String geteMail() {
        return eMail;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public ContactShortDTO() {
    }

    public ContactShortDTO(Integer id, String fullName, String dateOfBirth, String address, String company, String eMail) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.company = company;
        this.eMail = eMail;
    }
}
