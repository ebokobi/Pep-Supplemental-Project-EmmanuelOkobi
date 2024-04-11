package com.revature.exceptions;

public class UserFailException extends RuntimeException{
    public UserFailException(String message) {
        super(message);
    }
}
