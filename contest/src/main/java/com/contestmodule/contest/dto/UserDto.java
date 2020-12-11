package com.contestmodule.contest.dto;

import com.contestmodule.contest.entity.User;

public class UserDto {

    private String firstName;
    private String lastName;
    private String password;
    private String adress;
    private String email;

    public User getUserFromDto() {
        User user = new User();
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setPassword(password);
        user.setAddress(adress);
        user.setEmail(email);

        return user;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
