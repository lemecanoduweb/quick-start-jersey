package com.lemecanoduweb.quickstartjersey.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super(String.format("User %s is not found !", username));
    }
}
