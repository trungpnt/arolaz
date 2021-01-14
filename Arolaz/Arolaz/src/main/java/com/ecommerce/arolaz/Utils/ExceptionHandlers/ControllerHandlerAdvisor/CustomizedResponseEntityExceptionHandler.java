package com.ecommerce.arolaz.Utils.ExceptionHandlers.ControllerHandlerAdvisor;

import com.ecommerce.arolaz.Utils.ExceptionHandlers.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserWithPhoneNumberAlreadyExistsException.class)
    public final ResponseEntity<Object> handleUserWithPhoneNumberAlreadyExistsExceptions(UserWithPhoneNumberAlreadyExistsException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(BrandNameAlreadyExistsException.class)
    public final ResponseEntity<Object> handleBrandNameAlreadyExistsExceptions(BrandNameAlreadyExistsException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ColorNameAlreadyExistsException.class)
    public final ResponseEntity<Object> handleColorNameAlreadyExistsExceptions(ColorNameAlreadyExistsException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserRoleNotFoundException.class)
    public final ResponseEntity<Object> handleUserRoleNotFoundExceptions(UserRoleNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserWithEmailAlreadyExistsException.class)
    public final ResponseEntity<Object> handleUserWithEmailAlreadyExistsException(UserWithEmailAlreadyExistsException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ProductIdNotProvidedException.class)
    public final ResponseEntity<Object> handleProductIdNotFoundExceptions(ProductIdNotProvidedException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(UserNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InventoryNotFoundException.class)
    public final ResponseEntity<Object> handleInventoryNotFoundExceptions(InventoryNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public final ResponseEntity<Object> handleCategoryNotFoundExceptions(CategoryNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientInventoryQuantityException.class)
    public final ResponseEntity<Object> handleInsufficientInventoryQuantityExceptions(InsufficientInventoryQuantityException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductSizeNotFoundException.class)
    public final ResponseEntity<Object> handleProductSizeNotFoundExceptions(ProductSizeNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BrandNotFoundException.class)
    public final ResponseEntity<Object> handleBrandNotFoundExceptions(BrandNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SizeNotFoundException.class)
    public final ResponseEntity<Object> handleSizeNotFoundExceptions(SizeNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ColorNotFoundException.class)
    public final ResponseEntity<Object> handleColorNotFoundExceptions(ColorNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public final ResponseEntity<Object> handleInvalidTokenExceptions(InvalidTokenException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handleProductNotFoundExceptions(ProductNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminRoleNotFoundException.class)
    public final ResponseEntity<Object> handleAdminRoleNotFoundExceptions(AdminRoleNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
