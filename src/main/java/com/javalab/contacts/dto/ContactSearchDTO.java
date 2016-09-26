package com.javalab.contacts.dto;


public class ContactSearchDTO {
    private String firstName;
    private String lastName;
    private String secondName;
    private String dateOfBirthLessThan;
    private String dateOfBirthGreaterThan;
    private String sex;
    private String nationality;
    private String martialStatus;
    private String country;
    private String town;
    private Integer zipCode;
    private String street;
    private Integer houseNumber;
    private Integer flatNumber;
    private String orderBy;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getDateOfBirthLessThan() {
        return dateOfBirthLessThan;
    }

    public void setDateOfBirthLessThan(String dateOfBirthLessThan) {
        this.dateOfBirthLessThan = dateOfBirthLessThan;
    }

    public String getDateOfBirthGreaterThan() {
        return dateOfBirthGreaterThan;
    }

    public void setDateOfBirthGreaterThan(String dateOfBirthGreaterThan) {
        this.dateOfBirthGreaterThan = dateOfBirthGreaterThan;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}