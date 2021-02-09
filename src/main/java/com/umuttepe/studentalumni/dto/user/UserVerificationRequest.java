package com.umuttepe.studentalumni.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserVerificationRequest implements Serializable {
    private String code;
}