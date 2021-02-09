package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.JobCategoryDao;
import com.umuttepe.studentalumni.dao.entity.JobCategoryEntity;
import com.umuttepe.studentalumni.dao.entity.JobEntity;
import com.umuttepe.studentalumni.exception.job.JobNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCategoryService {

    @Autowired
    private JobCategoryDao jobCategoryDao;

    public List<JobCategoryEntity> getCategories() {
        return jobCategoryDao.findAll();
    }

    public JobCategoryEntity getCategory(Long id) throws JobNotFoundException {
        return jobCategoryDao.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Anket bulunamadı!"));
    }

    public JobCategoryEntity addCategory(JobCategoryEntity job) throws Exception {
        return jobCategoryDao.save(job);
    }

    public JobCategoryEntity updateCategory(JobCategoryEntity job) {
        return jobCategoryDao.save(job);
    }

    public void deleteCategory(Long id) {
        jobCategoryDao.deleteById(id);
    }
}
