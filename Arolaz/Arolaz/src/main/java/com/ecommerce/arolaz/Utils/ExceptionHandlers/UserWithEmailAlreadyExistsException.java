package com.ecommerce.arolaz.Utils.ExceptionHandlers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserWithEmailAlreadyExistsException extends RuntimeException{
    public UserWithEmailAlreadyExistsException(String message) {
        super(message);
    }
}
