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

    public PhoneNumberDTO(Integer id, Integer countryCode, Integer operatorCode, Integer number,
                          String type, String comment, String fullNumber) {
        this.id = id;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
        this.fullNumber = fullNumber;
        this.type = type;
        this.comment = comment;
    }

    public PhoneNumberDTO(Integer id, Integer countryCode, Integer operatorCode, Integer number, String type, String comment) {
        this.id = id;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
        this.type = type;
        this.comment = comment;
    }
}
