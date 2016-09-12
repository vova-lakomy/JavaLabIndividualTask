package com.javalab.contacts.dto;


public class AddressDTO {

    private Integer id;

    private String country;

    private String town;

    private String street;

    private int houseNumber;

    private int flatNumber;

    private int zipCode;

    public Integer getId() {
        return id;
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

    public AddressDTO(Integer id, String country, String town, String street, int houseNumber, int flatNumber, int zipCode) {
        this.id = id;
        this.country = country;
        this.town = town;
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
        this.zipCode = zipCode;
    }
}
