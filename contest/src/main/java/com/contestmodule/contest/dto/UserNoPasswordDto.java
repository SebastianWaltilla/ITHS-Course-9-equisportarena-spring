package com.contestmodule.contest.dto;

import com.contestmodule.contest.entity.User;

public class UserNoPasswordDto {

    private String firstName;
    private String lastName;
    private String address;
    private String email;

    public UserNoPasswordDto() {
    }

    public UserNoPasswordDto(String firstName, String lastName, String address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    public UserNoPasswordDto getUserNoPasswordDtoFromUser(User user) {
        this.setFirstName(user.getFirstname());
        this.setLastName(user.getLastname());
        this.setAddress(user.getAddress());
        this.setEmail(user.getEmail());
        return this;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

