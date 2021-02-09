package com.umuttepe.studentalumni.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String username;

    private String phone;

    private String password;

    private String studentNumber;

    private Date graduationDate;

    private String profileImage;

    private Boolean isVerified;

    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;

    public UserDTO() {
    }
}
