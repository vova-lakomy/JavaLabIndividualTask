package com.javalab.contacts.dto;


public class ContactSearchDTO {
    private String firstName;
    private String lastName;
    private String secondName;
    private String dateOfBirthLessThan;
    private String dateOfBirthGreaterThan;
    private String sex;
    private String nationality;
    private String maritalStatus;
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("{");
        if (firstName != null) {
            output.append("firstName='");
            output.append(firstName);
            output.append("', ");
        }
        if (lastName != null) {
            output.append("lastName='");
            output.append(lastName);
            output.append("', ");
        }
        if (secondName != null) {
            output.append("secondName='");
            output.append(secondName);
            output.append("', ");
        }
        if (dateOfBirthGreaterThan != null) {
            output.append("dateOfBirth>'");
            output.append(dateOfBirthGreaterThan);
            output.append("', ");
        }
        if (dateOfBirthLessThan != null) {
            output.append("dateOfBirth<'");
            output.append(dateOfBirthLessThan);
            output.append("', ");
        }
        if (sex != null) {
            output.append("sex='");
            output.append(sex);
            output.append("', ");
        }
        if (nationality != null) {
            output.append("nationality='");
            output.append(nationality);
            output.append("', ");
        }if (maritalStatus != null) {
            output.append("maritalStatus='");
            output.append(maritalStatus);
            output.append("', ");
        }
        if (country != null) {
            output.append("country='");
            output.append(country);
            output.append("', ");
        }
        if (town != null) {
            output.append("town='");
            output.append(town);
            output.append("', ");
        }
        if (zipCode != null) {
            output.append("zipCode='");
            output.append(zipCode);
            output.append("', ");
        }
        if (street != null) {
            output.append("street='");
            output.append(street);
            output.append("', ");
        }
        if (houseNumber != null) {
            output.append("houseNumber='");
            output.append(houseNumber);
            output.append("', ");
        }
        if (flatNumber != null) {
            output.append("flatNumber='");
            output.append(flatNumber);
            output.append("', ");
        }
        if (orderBy != null) {
            output.append("orderBy='");
            output.append(orderBy);
            output.append("', ");
        }
        output.append('}');
        return output.toString();
    }
}