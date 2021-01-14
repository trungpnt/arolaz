package com.ecommerce.arolaz.Utils.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ColorNameAlreadyExistsException extends RuntimeException{
    public ColorNameAlreadyExistsException(String message) {
        super(message);
    }
}
