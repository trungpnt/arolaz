package com.ecommerce.arolaz.SecurityUser.Service;

import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SecurityUserService {

    Optional<String> signIn(String requiredEntry, String password);
    Optional<String> signUp(String email, String fullName, String phone, String password,String address);
    Optional<SecurityUser> signInForTesting(String requiredEntry, String password);
    Optional<SecurityUser> findByEmail(String email);
    Optional<SecurityUser> findByFullName(String fullName);
    Optional<SecurityUser> findByPhoneNumber(String phone);
    String formatToken(Optional<String> token);
    boolean isNumeric (String requiredInput);
    Optional<SecurityUser>  signInWithToken(String token);
    List<SecurityUser> getAll();
    boolean checkUniqueEmailAndPhoneNumber(String phone, String email);
    Optional<SecurityUser> addNewUser(SecurityUser securityUser);
    String extractPhoneFromToken(String token);
    void isValidToken(String token);
    Optional<SecurityUser> signUpForTesting(String phone, String email, String fullName, String password);
    void delete(SecurityUser securityUser);
    void deleteByUserId(ObjectId userId);
    Optional<SecurityUser> updateUser(SecurityUser securityUser);
    Optional<SecurityUser> findByUserId(ObjectId userId);
    Page<SecurityUser> findAll(Pageable pageable);
}
