package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dao.entity.UserVerificationEntity;
import com.umuttepe.studentalumni.dto.user.UserVerificationRequest;
import com.umuttepe.studentalumni.dto.user.UserVerificationResponse;
import com.umuttepe.studentalumni.exception.user.UserVerificationNotFoundException;
import com.umuttepe.studentalumni.service.JwtUserDetailsService;
import com.umuttepe.studentalumni.service.UserService;
import com.umuttepe.studentalumni.service.UserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user-verifications")
public class UserVerificationController {
    @Autowired
    private UserVerificationService userVerificationService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping
    public UserVerificationResponse updateCode(@RequestBody UserVerificationRequest verificationRequest) throws UserVerificationNotFoundException {
        UserVerificationEntity verification = userVerificationService.checkCode(verificationRequest.getCode());
        UserEntity user = verification.getUser();
        user.setIsVerified(true);
        userDetailsService.update(user);
        UserVerificationResponse res = new UserVerificationResponse();
        res.setStatus(true);
        return res;
    }
}
