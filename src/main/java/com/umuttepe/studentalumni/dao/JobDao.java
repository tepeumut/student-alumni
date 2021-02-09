package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.JobEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDao extends JpaRepository<JobEntity, Long> {
    public List<JobEntity> findAllByUser(UserEntity user);
}
