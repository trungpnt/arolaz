package com.ecommerce.arolaz.Arolaz.SecurityUser.RequestResponseModels;

import javax.validation.constraints.NotNull;

public class LoginDto {
    @NotNull
    private String phone;

    public LoginDto(@NotNull String phone, @NotNull String email, @NotNull String password, String firstName, String lastName){
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    private String email;

    @NotNull
    private String password;

    public LoginDto(@NotNull String phone, @NotNull String email, @NotNull String password) {
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    private String firstName;

    private String lastName;

    private String username;

    /**
     * Default constructor
     */
    protected LoginDto() {
    }

    /**
     * Partial constructor
     * @param username
     * @param password
     */
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String returnUsername(String s){
        return username = s;
    }

    /**
     * Full constructor
     * @param username
     * @param password
     */
    public LoginDto(String username, String password, String firstName, String lastName) {
        this(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
