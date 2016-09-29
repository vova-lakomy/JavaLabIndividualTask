package com.javalab.contacts.model;


public class ContactAddress {

    private Integer id;
    private String country;
    private String town;
    private String street;
    private Integer houseNumber;
    private Integer flatNumber;
    private Integer zipCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactAddress that = (ContactAddress) o;

        if (houseNumber != that.houseNumber) return false;
        if (flatNumber != that.flatNumber) return false;
        if (zipCode != that.zipCode) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (town != null ? !town.equals(that.town) : that.town != null) return false;
        return street != null ? street.equals(that.street) : that.street == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ContactAddress{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", town='" + town + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", flatNumber=" + flatNumber +
                ", zipCode=" + zipCode +
                '}';
    }
}
