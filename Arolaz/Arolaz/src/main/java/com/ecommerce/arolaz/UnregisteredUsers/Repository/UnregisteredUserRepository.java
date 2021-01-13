package com.ecommerce.arolaz.UnregisteredUsers.Repository;

import com.ecommerce.arolaz.UnregisteredUsers.Model.UnregisteredUser;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnregisteredUserRepository extends PagingAndSortingRepository<UnregisteredUser, ObjectId> {
    Optional<UnregisteredUser> findByPhoneNumberAndEmail(String phone, String email);
}
