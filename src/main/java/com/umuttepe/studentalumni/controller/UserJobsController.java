package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dao.entity.UserJobEntity;
import com.umuttepe.studentalumni.dto.user.UserJobDTO;
import com.umuttepe.studentalumni.exception.user.UserJobException;
import com.umuttepe.studentalumni.exception.user.UserNotFoundException;
import com.umuttepe.studentalumni.service.JwtUserDetailsService;
import com.umuttepe.studentalumni.service.UserJobService;
import com.umuttepe.studentalumni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/user-jobs")
public class UserJobsController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserJobService jobService;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping
    public UserJobEntity addUserJob(Authentication auth, @Valid @RequestBody UserJobDTO jobDTO) throws UserNotFoundException {
        UserEntity user = userDetailsService.findUserByUsername(auth.getName());
        UserJobEntity job = new UserJobEntity();
        job.setName(jobDTO.getName());
        job.setPosition(jobDTO.getPosition());
        job.setDescription(jobDTO.getDescription());
        job.setStartDate(jobDTO.getStartDate());
        job.setEndDate(jobDTO.getEndDate());
        job.setUser(user);
        return jobService.saveUserJob(job);
    }

    @GetMapping("{id}")
    public UserJobEntity getUserJob(Authentication auth, @PathVariable Long id) throws UserJobException, UserNotFoundException {
        UserEntity user = userDetailsService.findUserByUsername(auth.getName());
        return jobService.getUserJobwithUser(id, user);
    }

    @PutMapping("{id}")
    public UserJobEntity updateUserJob(Authentication auth, @PathVariable Long id,@RequestBody @Valid  UserJobDTO userJob) throws UserJobException, UserNotFoundException {
        UserEntity user = userDetailsService.findUserByUsername(auth.getName());
        UserJobEntity job = jobService.getUserJobwithUser(id, user);
        job.setName(userJob.getName());
        job.setPosition(userJob.getPosition());
        job.setDescription(userJob.getDescription());
        job.setStartDate(userJob.getStartDate());
        job.setEndDate(userJob.getEndDate());
        return jobService.saveUserJob(job);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserJob(Authentication auth, @PathVariable Long id) throws UserJobException, UserNotFoundException {
        UserEntity user = userDetailsService.findUserByUsername(auth.getName());
        jobService.getUserJobwithUser(id, user);
        jobService.deleteUserJob(id);
        return ResponseEntity.ok("ok!");
    }
}
