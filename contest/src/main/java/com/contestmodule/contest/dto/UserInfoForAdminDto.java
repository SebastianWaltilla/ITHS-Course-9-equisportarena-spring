package com.contestmodule.contest.dto;

import java.time.LocalDate;

public class UserInfoForAdminDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private LocalDate registeredDate;
    private LocalDate lastLoggedIn;
    private boolean isEmailVerified;

    public UserInfoForAdminDto() {
    }

    public UserInfoForAdminDto(Long id, String firstName,
                               String lastName,
                               String address, String email,
                               LocalDate registeredDate,
                               LocalDate lastLoggedIn,
                               boolean isEmailVerified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.registeredDate = registeredDate;
        this.lastLoggedIn = lastLoggedIn;
        this.isEmailVerified = isEmailVerified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    public LocalDate getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(LocalDate lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }
}
