package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.JobApplyFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplyFileDao extends JpaRepository<JobApplyFileEntity, Long> {
}
