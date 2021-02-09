package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.JobApplyDao;
import com.umuttepe.studentalumni.dao.entity.JobApplyEntity;
import com.umuttepe.studentalumni.dao.entity.JobApplyStatus;
import com.umuttepe.studentalumni.dao.entity.JobEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dto.job.JobApplyUpdateDTO;
import com.umuttepe.studentalumni.exception.job.JobNotFoundException;
import com.umuttepe.studentalumni.exception.user.UserNotFoundException;
import com.umuttepe.studentalumni.service.JobApplyService;
import com.umuttepe.studentalumni.service.JobService;
import com.umuttepe.studentalumni.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/job-applies")
public class JobApplyController {
    @Autowired
    private JobApplyService jobApplyService;
    @Autowired
    private JobService jobService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @GetMapping("/{id}")
    public List<JobApplyEntity> findMyJobs(@PathVariable(name = "id") Long id, Authentication authentication) throws UserNotFoundException, JobNotFoundException {
        JobEntity job = jobService.getJob(id);
        return jobApplyService.getJobApply(job);
    }

    @PutMapping("/{id}")
    public JobApplyEntity updateApply(@PathVariable(name = "id") Long id, Authentication authentication, @RequestBody JobApplyUpdateDTO apply) throws UserNotFoundException, JobNotFoundException {
        JobApplyEntity jobApply = jobApplyService.getJobApply(id);
        jobApply.setStatus(apply.getStatus());
        return jobApplyService.save(jobApply);
    }


    @GetMapping("/my-applied")
    public List<JobApplyEntity> findMyJobs(Authentication authentication) throws UserNotFoundException {
        UserEntity user = userDetailsService.findUserByUsername(authentication.getName());
        return jobApplyService.getMyJobs(user);
    }
}
