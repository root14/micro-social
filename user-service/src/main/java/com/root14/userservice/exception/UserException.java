package com.root14.userservice.exception;


import lombok.*;

@Getter
@Setter
@Builder
public class UserException extends RuntimeException {
    Exception exception;
    Integer errorCode;
    String errorMessage;
}
