package com.ecommerce.arolaz.Arolaz.SecurityUser.Service;

import com.ecommerce.arolaz.Arolaz.SecurityUser.Model.SecurityUser;

import java.util.List;
import java.util.Optional;

public interface SecurityUserService {

    Optional<String> signIn(String requiredEntry, String password);
    Optional<String> signUp(String email, String firstName, String lastName, String phone, String password,String address);
    Optional<SecurityUser> signUpToUser(String email, String phone, String firstName, String lastName, String password);
    Optional<SecurityUser> signInToUser(String requiredEntry, String password);
    Optional<SecurityUser> findByEmail(String email);
    Optional<SecurityUser> findByPhone(String phone);
    Optional<SecurityUser> findByFirstName(String firstName);
    String formatToken(Optional<String> token);
    boolean isNumeric (String requiredInput);
    Optional<SecurityUser>  signInWithToken(String token);
    List<SecurityUser> getAll();
    String extractPhoneFromToken(String token);
    Boolean isValidToken(String token);
    void updateUser(SecurityUser securityUser);

}
