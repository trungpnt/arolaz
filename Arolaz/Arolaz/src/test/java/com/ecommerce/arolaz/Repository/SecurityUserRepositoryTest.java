package com.ecommerce.arolaz.Repository;

import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.Repository.SecurityUserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SecurityUserRepositoryTest {
    @Autowired
    private SecurityUserRepository repository;

    @Test
    public void testFindByEmail() {

        Optional<SecurityUser> user = repository.findByEmail("abc123@gmail.com");
        if(user.isPresent()){
            assertTrue(user.get() != null);
        }

        user = repository.findByEmail("nobody@gmail.com");
        if(!user.isPresent()){
            assertFalse(user.isPresent());
        }
    }
}
