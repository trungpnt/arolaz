package com.ecommerce.arolaz.Utils.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdminRoleNotFoundException extends RuntimeException{
    public AdminRoleNotFoundException(String message) {
        super(message);
    }
}
