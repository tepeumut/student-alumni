package com.umuttepe.studentalumni.exception.user;

public class UserJobException extends Exception {
    public UserJobException(String message) {
        super(message);
    }

    public UserJobException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserJobException(Throwable cause) {
        super(cause);
    }

    public UserJobException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
