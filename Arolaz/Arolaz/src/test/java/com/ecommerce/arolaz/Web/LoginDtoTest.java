package com.ecommerce.arolaz.Web;

import com.ecommerce.arolaz.SecurityUser.RequestResponseModels.LoginDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class LoginDtoTest {

    @Test
    public void testAll() {
        LoginDto dto = new LoginDto("user@gmail.com","User@1234");
        assertThat(dto.getEmail(), is("user@gmail.com"));
        assertThat(dto.getPassword(), is("User@1234"));
    }
}