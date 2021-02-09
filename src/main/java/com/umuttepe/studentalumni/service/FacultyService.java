package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.FacultyDao;
import com.umuttepe.studentalumni.dao.entity.FacultyEntity;
import com.umuttepe.studentalumni.exception.faculty.FacultyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyDao facultyDao;

    public List<FacultyEntity> getAllFaculties() {
        return facultyDao.findAll();
    }

    public FacultyEntity getFaculty(Long id) throws FacultyNotFoundException {
        return facultyDao.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException("Fakülte bulunamadı!"));
    }

    public FacultyEntity saveFaculty(FacultyEntity faculty) {
        return facultyDao.save(faculty);
    }

    public FacultyEntity updateFaculty(FacultyEntity faculty) {
        return facultyDao.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyDao.deleteById(id);
    }
}
