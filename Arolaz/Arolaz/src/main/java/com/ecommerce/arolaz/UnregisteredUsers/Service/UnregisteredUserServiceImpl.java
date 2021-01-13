package com.ecommerce.arolaz.UnregisteredUsers.Service;

import com.ecommerce.arolaz.UnregisteredUsers.Model.UnregisteredUser;
import com.ecommerce.arolaz.UnregisteredUsers.Repository.UnregisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UnregisteredUserServiceImpl implements UnregisteredUserService {
    @Autowired
    private UnregisteredUserRepository unregisteredUserRepository;

    public UnregisteredUser addNewUnregisteredUser(UnregisteredUser unregisteredUser) {
        return unregisteredUserRepository.save(unregisteredUser);
    }

    public Optional<UnregisteredUser> findByEmailAndPhone(String email, String phone){
        Optional<UnregisteredUser> unregisteredUser = unregisteredUserRepository.findByPhoneNumberAndEmail(email,phone);
        return unregisteredUser;
    }
}
