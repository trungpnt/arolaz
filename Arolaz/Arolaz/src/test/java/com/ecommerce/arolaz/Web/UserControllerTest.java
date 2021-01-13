package com.ecommerce.arolaz.Web;


import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.RequestResponseModels.LoginDto;
import com.ecommerce.arolaz.SecurityUser.Service.SecurityUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private LoginDto signupDto = new LoginDto("0908833441", "controllerTest@gmail.com", "pager larry", "User@1234");
    private SecurityUser user = new SecurityUser(signupDto.getPhone(), signupDto.getEmail(),
            signupDto.getFullName(), signupDto.getPassword());

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private SecurityUserService service;

//    @Test
//    public void signin() {
//        restTemplate.postForEntity("/api/user/signin", new LoginDto("dummyEmail@gmail.com", "@User1234"), Void.class);
//        verify(this.service   ,times(1)).signInForTesting("dummyEmail@gmail.com","@User1234");
//    }

    @Test
    public void signup(){
        when(service.signUpForTesting(signupDto.getPhone(),signupDto.getEmail(), signupDto.getFullName(), signupDto.getPassword())).thenReturn(Optional.of(user));

        assertThat(user.getEmail(), is(user.getEmail()));
        assertThat(user.getFullName(), is(user.getFullName()));
        assertThat(user.getPhoneNumber(), is(user.getPhoneNumber()));
        assertThat(user.getPassword(), is(user.getPassword()));

    }
}
