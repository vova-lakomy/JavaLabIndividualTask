package com.javalab.contacts.dto;


import java.util.Collection;

public class ContactDTO {

    private String fullName;

    private String dateOfBirth;

    private Collection<String> addresses;

    private String company;

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

    public ContactDTO(String fullName, String dateOfBirth, Collection<String> addresses, String company) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.addresses = addresses;
        this.company = company;
    }
}
