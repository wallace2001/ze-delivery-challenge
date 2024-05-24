package com.wallace.deliverychallenge.infraestructure.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

public class RestControllerAdviceExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage()));

        return new ApiError("Error on validation", errors);
    }

//    @ExceptionHandler({InvalidCredentialsException.class, AuthenticationException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ApiError handleUnauthorizedException(Exception ex) {
//        return new ApiError("Access denied: " + ex.getLocalizedMessage());
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGenericException(Exception ex) {
        return new ApiError("An internal server error occurred: " + ex.getLocalizedMessage());
    }
}
