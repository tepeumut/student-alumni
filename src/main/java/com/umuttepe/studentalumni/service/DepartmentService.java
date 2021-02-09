package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.DepartmentDao;
import com.umuttepe.studentalumni.dao.entity.DepartmentEntity;
import com.umuttepe.studentalumni.dao.entity.FacultyEntity;
import com.umuttepe.studentalumni.exception.department.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    public List<DepartmentEntity> getAllDepartmentFaculties(FacultyEntity faculty) {
        return departmentDao.findAllByFaculty(faculty);
    }

    public DepartmentEntity getDepartment(Long id) throws DepartmentNotFoundException {
        return departmentDao.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Bölüm bulunamadı!"));
    }

    public DepartmentEntity saveDepartment(DepartmentEntity faculty) {
        return departmentDao.save(faculty);
    }

    public DepartmentEntity updateDepartment(DepartmentEntity faculty) {
        return departmentDao.save(faculty);
    }

    public void deleteDepartment(Long id) {
        departmentDao.deleteById(id);
    }
}