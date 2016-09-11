package com.javalab.contacts.dto;


import java.util.Collection;

public class ContactShortDTO {

    private Integer id;

    private String fullName;

    private String dateOfBirth;

    private Collection<String> addresses;

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

    public Collection<String> getAddresses() {
        return addresses;
    }

    public String getCompany() {
        return company;
    }

    public ContactShortDTO(Integer id, String fullName, String dateOfBirth, Collection<String> addresses, String company) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.addresses = addresses;
        this.company = company;
    }
}
