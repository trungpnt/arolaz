package com.ecommerce.arolaz.UnregisteredUsers.Service;

import com.ecommerce.arolaz.UnregisteredUsers.Model.UnregisteredUser;

import java.util.Optional;

public interface UnregisteredUserService {
    UnregisteredUser addNewUnregisteredUser(UnregisteredUser unregisteredUser);
    Optional<UnregisteredUser> findByEmailAndPhone(String email, String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
