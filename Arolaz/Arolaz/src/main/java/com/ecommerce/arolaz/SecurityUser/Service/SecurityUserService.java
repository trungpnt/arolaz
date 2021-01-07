package com.ecommerce.arolaz.SecurityUser.Service;

import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;

import java.util.List;
import java.util.Optional;

public interface SecurityUserService {

    Optional<String> signIn(String requiredEntry, String password);
    Optional<String> signUp(String email, String fullName, String phone, String password,String address);
    Optional<SecurityUser> signUpToUser(String email, String phone, String firstName, String lastName, String password);
    Optional<SecurityUser> signInToUser(String requiredEntry, String password);
    Optional<SecurityUser> findByEmail(String email);
    Optional<SecurityUser> findByFullName(String fullName);
    Optional<SecurityUser> findByPhoneNumber(String phone);
    String formatToken(Optional<String> token);
    boolean isNumeric (String requiredInput);
    Optional<SecurityUser>  signInWithToken(String token);
    List<SecurityUser> getAll();
    String extractPhoneFromToken(String token);
    void isValidToken(String token);
    void updateUser(SecurityUser securityUser);

}
