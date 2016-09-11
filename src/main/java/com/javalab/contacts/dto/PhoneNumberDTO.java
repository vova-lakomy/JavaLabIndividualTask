package com.javalab.contacts.dto;

public class PhoneNumberDTO {

    Integer id;

    private String number;

    private String type;

    private String comment;


    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public PhoneNumberDTO(Integer id, String number, String type, String comment) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.comment = comment;
    }
}
