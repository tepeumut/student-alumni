package com.umuttepe.studentalumni.exception.job;

public class JobApplyException extends Exception {
    public JobApplyException(String message) {
        super(message);
    }

    public JobApplyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobApplyException(Throwable cause) {
        super(cause);
    }

    public JobApplyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
