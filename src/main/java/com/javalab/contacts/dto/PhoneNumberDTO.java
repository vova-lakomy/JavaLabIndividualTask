package com.javalab.contacts.dto;

public class PhoneNumberDTO {

    private Integer id;
    private Integer countryCode;
    private Integer operatorCode;
    private Integer number;
    private String fullNumber;
    private String type;
    private String comment;

    public Integer getId() {
        return id;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public Integer getNumber() {
        return number;
    }

    public String getFullNumber() {
        return fullNumber;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setFullNumber(String fullNumber) {
        this.fullNumber = fullNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
