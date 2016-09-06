package com.javalab.contacts.model;


import com.javalab.contacts.model.enumerations.PhoneType;

public class PhoneNumber {

    private Integer id;

    private int countryCode;

    private int operatorCode;

    private int phoneNumber;

    private PhoneType phoneType;

    private String phoneComment;

    public int getCountryCode() {
        return countryCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(int operatorCode) {
        this.operatorCode = operatorCode;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
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

    public PhoneNumber() {
    }

    public PhoneNumber(Integer id, int countryCode, int operatorCode, int phoneNumber, PhoneType phoneType, String phoneComment) {
        this.id = id;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.phoneComment = phoneComment;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id=" + id +
                " +" + countryCode +
                "(" + operatorCode +
                ")" + phoneNumber +
                ", phoneType=" + phoneType +
                ", phoneComment='" + phoneComment + '\'' +
                '}';
    }
}
