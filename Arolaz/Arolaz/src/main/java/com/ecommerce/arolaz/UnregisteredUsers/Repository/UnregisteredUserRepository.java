package com.ecommerce.arolaz.UnregisteredUsers.Repository;

import com.ecommerce.arolaz.UnregisteredUsers.Model.UnregisteredUser;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UnregisteredUserRepository extends PagingAndSortingRepository<UnregisteredUser, ObjectId> {
}
