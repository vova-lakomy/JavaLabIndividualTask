package com.javalab.contacts.model;


import com.javalab.contacts.model.enumerations.PhoneType;

public class PhoneNumber {

    private Integer id;
    private Integer countryCode;
    private Integer operatorCode;
    private Integer phoneNumber;
    private PhoneType phoneType;
    private String phoneComment;

    public Integer getCountryCode() {
        return countryCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneComment() {
        return phoneComment;
    }

    public void setPhoneComment(String phoneComment) {
        this.phoneComment = phoneComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (countryCode != that.countryCode) return false;
        if (operatorCode != that.operatorCode) return false;
        if (phoneNumber != that.phoneNumber) return false;
        if (phoneType != that.phoneType) return false;
        return phoneComment != null ? phoneComment.equals(that.phoneComment) : that.phoneComment == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id=" + id +
                ", countryCode=" + countryCode +
                ", operatorCode=" + operatorCode +
                ", phoneNumber=" + phoneNumber +
                ", phoneType=" + phoneType +
                ", phoneComment='" + phoneComment + '\'' +
                '}';
    }
}
