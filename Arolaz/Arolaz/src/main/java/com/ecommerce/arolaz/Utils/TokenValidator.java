package com.ecommerce.arolaz.Utils;

import com.ecommerce.arolaz.Utils.ExceptionHandlers.AdminRoleNotFoundException;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.UserRoleNotFoundException;
import com.ecommerce.arolaz.Utils.Security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {
    @Autowired
    private JwtProvider jwtProvider;

    public TokenValidator() { }

    private static String USER = "USER";

    private static String ADMIN = "ADMIN";
    /**
     * This class check if token is valid AND specified role (ADMIN,USER) authorized
     * */
    public void validateTokenAdminAuthorization(String token){
        if(jwtProvider.isValidToken(token)){
            String adminRole = jwtProvider.getRoles(token).get(0).getAuthority();
            if(adminRole.compareTo(ADMIN) != 0){
                throw new AdminRoleNotFoundException("TOKEN DOES NOT CONTAIN ADMIN AUTHORITY");
            }
        }
    }

    public void validateTokenUserAuthorization(String token){
        if(jwtProvider.isValidToken(token)){
            String userRole = jwtProvider.getRoles(token).get(0).getAuthority();
            if(userRole.compareTo(USER) != 0){
                throw new UserRoleNotFoundException("TOKEN DOES NOT CONTAIN USER AUTHORITY");
            }
        }
    }

}
