package com.ecommerce.arolaz.SecurityUser.Model;

import com.ecommerce.arolaz.SecurityRole.Model.SecurityRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Size;
import java.util.List;

@QueryEntity
@Document(collection = "security_user")
public class SecurityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    private String email;

    @Size(min = 7)
    private String fullName;

    private String phoneNumber;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    protected SecurityUser(){}

    private List<SecurityRole> roles;

    public SecurityUser(String email, String fullName, String phoneNumber, String password, String address, AuthProvider provider, List<SecurityRole> roles) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.provider = provider;
        this.roles = roles;
    }

    public SecurityUser(String phone, String email, String fullName, String encode) {
        this.phoneNumber = phone;
        this.email = email;
        this.fullName = fullName;
        this.password = encode;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public SecurityUser(String email, String fullName, String phoneNumber, String password, String address) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
    }

    public SecurityUser(String email, String fullName, String phoneNumber, String password, String address, List<SecurityRole> roles) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.roles = roles;
    }

    public ObjectId getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SecurityRole> roles) {
        this.roles = roles;
    }
}
