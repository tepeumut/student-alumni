package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.JobApplyEntity;
import com.umuttepe.studentalumni.dao.entity.JobApplyStatus;
import com.umuttepe.studentalumni.dao.entity.JobEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface JobApplyDao extends JpaRepository<JobApplyEntity, Long> {
    public boolean existsByStatusAndUserAndJob(JobApplyStatus status, UserEntity user, JobEntity job);
    public List<JobApplyEntity> findAllByUser(UserEntity user);
    public List<JobApplyEntity> findAllByJob(JobEntity job);
    @Transactional
    public void deleteByJob(JobEntity job);
}
