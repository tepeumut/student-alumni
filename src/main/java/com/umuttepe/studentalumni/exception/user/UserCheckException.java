package com.umuttepe.studentalumni.exception.user;

import org.springframework.validation.FieldError;

import java.util.List;

public class UserCheckException extends Exception {

    public List<FieldError> errors;

    public UserCheckException(List<FieldError> errors) {
        this.errors = errors;
    }

    public List<FieldError> getFieldErrors() {
        return this.errors;
    }
}
