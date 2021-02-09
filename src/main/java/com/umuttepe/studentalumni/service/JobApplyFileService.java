package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.JobApplyFileDao;
import com.umuttepe.studentalumni.dao.entity.JobApplyEntity;
import com.umuttepe.studentalumni.dao.entity.JobApplyFileEntity;
import com.umuttepe.studentalumni.dao.entity.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobApplyFileService {
    @Autowired
    private JobApplyFileDao jobApplyFileDao;

    public JobApplyFileEntity save(String file, JobApplyEntity jobapply) {
        JobApplyFileEntity jobApplyFile = new JobApplyFileEntity();
        jobApplyFile.setFile(file);
        jobApplyFile.setJob_apply(jobapply);
        return jobApplyFileDao.save(jobApplyFile);
    }
}
