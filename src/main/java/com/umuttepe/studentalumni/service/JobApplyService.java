package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.JobApplyDao;
import com.umuttepe.studentalumni.dao.entity.JobApplyEntity;
import com.umuttepe.studentalumni.dao.entity.JobApplyStatus;
import com.umuttepe.studentalumni.dao.entity.JobEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.exception.job.JobNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplyService {

    @Autowired
    private JobApplyDao jobApplyDao;

    public JobApplyEntity save(JobEntity job, UserEntity user, String about, JobApplyStatus status) {
        JobApplyEntity jobApply = new JobApplyEntity();
        jobApply.setJob(job);
        jobApply.setAbout(about);
        jobApply.setStatus(status);
        jobApply.setUser(user);
        return jobApplyDao.save(jobApply);
    }
    public JobApplyEntity getJobApply(Long id) throws JobNotFoundException {
        return jobApplyDao.findById(id)
                .orElseThrow(() -> new JobNotFoundException("İş başvurusu bulunamadı!"));
    }
    public List<JobApplyEntity> getMyJobs(UserEntity user) {
        return jobApplyDao.findAllByUser(user);
    }
    public List<JobApplyEntity> getJobApply(JobEntity job) {
        return jobApplyDao.findAllByJob(job);
    }
    public boolean checkJobApply(JobApplyStatus status, UserEntity user, JobEntity job) {
        return jobApplyDao.existsByStatusAndUserAndJob(status, user, job);
    }
    public void deleteAllByJob(JobEntity job) {
        jobApplyDao.deleteByJob(job);
    }
    public JobApplyEntity save(JobApplyEntity job) {
        return jobApplyDao.save(job);
    }
}
