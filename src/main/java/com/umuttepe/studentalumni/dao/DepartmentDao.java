package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.DepartmentEntity;
import com.umuttepe.studentalumni.dao.entity.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentDao extends JpaRepository<DepartmentEntity, Long> {
    public List<DepartmentEntity> findAllByFaculty(FacultyEntity faculty);
}
