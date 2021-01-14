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

        Optional<UnregisteredUser> tryFind = null;

        if(unregisteredUserRepository.count() == 0){
            return unregisteredUserRepository.save(unregisteredUser);
        }

        if(existsByEmail(unregisteredUser.getEmail()) && existsByPhone(unregisteredUser.getPhoneNumber())){
            tryFind = unregisteredUserRepository.findByPhoneNumberAndEmail(unregisteredUser.getPhoneNumber(), unregisteredUser.getEmail());
        }
        return unregisteredUserRepository.save(tryFind.get());
    }

    @Override
    public Optional<UnregisteredUser> findByEmailAndPhone(String email, String phone){
        Optional<UnregisteredUser> unregisteredUser = unregisteredUserRepository.findByPhoneNumberAndEmail(email,phone);
        return unregisteredUser;
    }

    @Override
    public boolean existsByEmail(String email){
        return unregisteredUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone){
        return unregisteredUserRepository.existsByPhoneNumber(phone);
    }
}
