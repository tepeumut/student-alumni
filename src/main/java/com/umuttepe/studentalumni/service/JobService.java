package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.JobDao;
import com.umuttepe.studentalumni.dao.entity.JobEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dto.job.JobAddDTO;
import com.umuttepe.studentalumni.dto.survey.SurveyAddDTO;
import com.umuttepe.studentalumni.exception.job.JobNotFoundException;
import com.umuttepe.studentalumni.exception.survey.SurveyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobDao jobDao;

    public List<JobEntity> getJobs() {
        return jobDao.findAll();
    }

    public List<JobEntity> getMyJobs(UserEntity user) {
        return jobDao.findAllByUser(user);
    }

    public JobEntity getJob(Long id) throws JobNotFoundException {
        return jobDao.findById(id)
                .orElseThrow(() -> new JobNotFoundException("İş bulunamadı!"));
    }

    public JobEntity addJob(JobEntity job) throws Exception {
        return jobDao.save(job);
    }

    public void deleteJob(Long id) throws Exception {
        jobDao.deleteById(id);
    }

    public JobEntity updateJob(JobEntity job) {
        return jobDao.save(job);
    }
}
