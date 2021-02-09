package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.JobCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobCategoryDao extends JpaRepository<JobCategoryEntity, Long> {
}
