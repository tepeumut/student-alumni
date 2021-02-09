package com.umuttepe.studentalumni.dto.user;

import com.umuttepe.studentalumni.dao.entity.UserEntity;

import java.io.Serializable;

public class JwtUserResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final UserEntity user;

    public JwtUserResponse(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return this.user;
    }
}