package com.umuttepe.studentalumni.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserVerificationResponse implements Serializable {
    private boolean status;
}