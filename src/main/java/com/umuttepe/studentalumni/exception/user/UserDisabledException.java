package com.umuttepe.studentalumni.exception.user;

public class UserDisabledException extends Exception {
    public UserDisabledException(String message) {
        super(message);
    }

    public UserDisabledException(String msg, Throwable e) {
        super(msg, e);
    }

}
