package com.ecommerce.arolaz.Utils.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductIdNotProvidedException extends RuntimeException{
    public ProductIdNotProvidedException(String message) {
        super(message);
    }
}
