package com.javalab.contacts.dto;


import java.util.Collection;

public class ContactShortDTO {

    private Integer id;

    private String fullName;

    private String dateOfBirth;

    private String address;

    private String company;

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

    public ContactShortDTO(Integer id, String fullName, String dateOfBirth, String address, String company) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.company = company;
    }
}
