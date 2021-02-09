package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyDao extends JpaRepository<FacultyEntity, Long> {
}
