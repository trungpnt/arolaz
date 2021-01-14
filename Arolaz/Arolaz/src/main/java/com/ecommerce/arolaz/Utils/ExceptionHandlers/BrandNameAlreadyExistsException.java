package com.ecommerce.arolaz.Utils.ExceptionHandlers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BrandNameAlreadyExistsException extends RuntimeException{
    public BrandNameAlreadyExistsException(String message) {
        super(message);
    }
}
