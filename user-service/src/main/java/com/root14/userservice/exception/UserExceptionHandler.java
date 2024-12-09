package com.root14.userservice.exception;

import com.mongodb.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserException.class})
    ResponseEntity<Object> handleConflict(UserException userException, WebRequest request) {
        return handleExceptionInternal(userException, userException.errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


}
