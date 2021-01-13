package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

import javax.validation.constraints.NotNull;

public class LoginDto {
    private String phone;

    private String email;

    private String fullName;

    private String password;

    public LoginDto(){}
    public LoginDto(String phone, String email, String fullName, String password) {
        this.phone = phone;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
