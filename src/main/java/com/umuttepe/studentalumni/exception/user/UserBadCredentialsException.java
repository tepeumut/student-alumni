package com.umuttepe.studentalumni.exception.user;

public class UserBadCredentialsException extends Exception {
    public UserBadCredentialsException(String msg, Throwable e) {
        super(msg, e);
    }
}
