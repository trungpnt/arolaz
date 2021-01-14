package com.ecommerce.arolaz.Repository;

import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.Repository.SecurityUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private SecurityUserRepository repository;

    @Test
    public void testFindByUsername() {
        Optional<SecurityUser> user = repository.findByFullName("admin");
        assertTrue(user.isPresent());

        user = repository.findByFullName("nobody");
        assertFalse(user.isPresent());
    }
}