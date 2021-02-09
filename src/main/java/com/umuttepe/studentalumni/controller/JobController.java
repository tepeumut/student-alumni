package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.entity.*;
import com.umuttepe.studentalumni.dto.job.JobAddDTO;
import com.umuttepe.studentalumni.dto.job.JobApplyFormAddDTO;
import com.umuttepe.studentalumni.dto.job.JobUpdateDTO;
import com.umuttepe.studentalumni.exception.job.JobApplyException;
import com.umuttepe.studentalumni.exception.job.JobApplyExistsException;
import com.umuttepe.studentalumni.exception.job.JobApplyFileNotFoundResourceException;
import com.umuttepe.studentalumni.exception.job.JobNotFoundException;
import com.umuttepe.studentalumni.exception.user.UserNotFoundException;
import com.umuttepe.studentalumni.service.*;
import com.umuttepe.studentalumni.storage.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private StorageService storageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobCategoryService jobCategoryService;

    @Autowired
    private JobApplyService jobApplyService;

    @Autowired
    private JobApplyFileService jobApplyFileService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @GetMapping
    public List<JobEntity> findAll() {
        return jobService.getJobs();
    }

    @GetMapping("/my-jobs")
    public List<JobEntity> findMyJobs(Authentication authentication) throws UserNotFoundException {
        UserEntity user = userDetailsService.findUserByUsername(authentication.getName());
        return jobService.getMyJobs(user);
    }

    @PostMapping
    public JobEntity add(@Valid @RequestBody JobAddDTO jobAdd, Authentication authentication) throws Exception {
        UserEntity user = userDetailsService.findUserByUsername(authentication.getName());
        JobCategoryEntity category = jobCategoryService.getCategory(jobAdd.getCategory_id());
        JobEntity job = convertAddToEntity(jobAdd);
        job.setUser(user);
        job.setCategory(category);
        return jobService.addJob(job);
    }


    @GetMapping("/{id}")
    public JobEntity find(@PathVariable Long id) throws JobNotFoundException {
        return jobService.getJob(id);
    }

    @PostMapping("/{id}/apply-forms")
    public JobApplyEntity addApplyForm(Authentication authentication, @PathVariable Long id, @RequestParam(name = "about") String about, @RequestParam(name = "files") MultipartFile[] files) throws JobNotFoundException, JobApplyExistsException, JobApplyFileNotFoundResourceException, JobApplyException, UserNotFoundException {
        if (files.length == 0) {
            throw new JobApplyFileNotFoundResourceException("Yüklemek için lütfen dosya seçin!");
        }
        if (about.isEmpty()) {
            throw new JobApplyException("Ön bilgi zorunlu");
        }
        JobEntity job = jobService.getJob(id);
        UserEntity user = userDetailsService.findUserByUsername(authentication.getName());
        if (jobApplyService.checkJobApply(JobApplyStatus.NEW, user, job)) {
            throw new JobApplyExistsException("Aktif bir başvurunuz bulunmaktadır.");
        }
        JobApplyEntity jobApply = jobApplyService.save(job, user, about, JobApplyStatus.NEW);
        Arrays.stream(files).forEach(file -> {
            String filename = UUID.randomUUID().toString();
            String fileName = filename + "." + storageService.getExtensionByApacheLib(file.getOriginalFilename());
            storageService.save(file, fileName);
            jobApplyFileService.save(fileName, jobApply);
        });
        return jobApply;
    }
    @PutMapping("/{id}")
    public JobEntity update(@PathVariable Long id, @RequestBody @Valid JobUpdateDTO job, Authentication authentication) throws Exception {
        UserEntity user = userDetailsService.findUserByUsername(authentication.getName());
        JobEntity newJob = jobService.getJob(id);
        JobCategoryEntity category = jobCategoryService.getCategory(job.getCategory_id());
        newJob.setJob_type(job.getJob_type());
        newJob.setName(job.getName());
        newJob.setCategory(category);
        newJob.setContent(job.getContent());
        newJob.setExperience(job.getExperience());
        newJob.setOffer_salary(job.getOffer_salary());
        newJob.setShort_description(job.getShort_description());
        newJob.setWorkLevel(job.getWorkLevel());
        if (user != newJob.getUser() && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ADMIN"))) {
            throw new Exception("Yetkisiz Erisim!");
        }
        return jobService.updateJob(newJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) throws Exception {
        UserEntity user = userDetailsService.findUserByUsername(authentication.getName());
        JobEntity job = jobService.getJob(id);
        if (user != job.getUser() && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ADMIN"))) {
            throw new Exception("Yetkisiz Erisim!");
        }
        jobApplyService.deleteAllByJob(job);
        jobService.deleteJob(id);
        return ResponseEntity.ok("ok");
    }

    private JobEntity convertAddToEntity(JobAddDTO jobDto){
        return modelMapper.map(jobDto, JobEntity.class);
    }
}
