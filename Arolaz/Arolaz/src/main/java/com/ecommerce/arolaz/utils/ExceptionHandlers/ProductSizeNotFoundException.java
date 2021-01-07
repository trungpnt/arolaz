package com.ecommerce.arolaz.utils.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductSizeNotFoundException extends RuntimeException{
    public ProductSizeNotFoundException(String message) {
        super(message);
    }
}
