package com.umuttepe.studentalumni.exception;

import com.umuttepe.studentalumni.exception.api.ApiError;
import com.umuttepe.studentalumni.exception.user.UserBadCredentialsException;
import com.umuttepe.studentalumni.exception.user.UserDisabledException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalErrorHandler extends RuntimeException {

    @ExceptionHandler(UserDisabledException.class)
    protected ResponseEntity<Object> handleDisabled(UserDisabledException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, "Hesap kapali", ex.getCause()));
    }

    @ExceptionHandler(UserBadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(UserBadCredentialsException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, "Kullanıcı adı veya şifre yanlıs", ex.getCause()));
    }


    @ExceptionHandler(RequestRejectedException.class)
    public ResponseEntity<Object> handleException(RequestRejectedException ex)
    {
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Sunucu Hatası", ex.getCause()));
    }
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
