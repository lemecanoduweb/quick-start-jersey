package com.lemecanoduweb.quickstartjersey.domain.exception;

public class UserAlreadyCreatedException extends RuntimeException {
    public UserAlreadyCreatedException(String username) {
        super(String.format("User %s is already created !", username));
    }
}
