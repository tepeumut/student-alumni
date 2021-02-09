package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.entity.DepartmentEntity;
import com.umuttepe.studentalumni.dao.entity.FacultyEntity;
import com.umuttepe.studentalumni.dto.faculty.FacultyDTO;
import com.umuttepe.studentalumni.exception.department.DepartmentNotFoundException;
import com.umuttepe.studentalumni.exception.faculty.FacultyNotFoundException;
import com.umuttepe.studentalumni.service.DepartmentService;
import com.umuttepe.studentalumni.service.FacultyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/faculty-departments")
public class DepartmentController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public List<DepartmentEntity> findAll(@PathVariable Long id) throws FacultyNotFoundException {
        FacultyEntity faculty = facultyService.getFaculty(id);
        return departmentService.getAllDepartmentFaculties(faculty);
    }

    @GetMapping("/{faculty_id}/departments/{id}")
    public DepartmentEntity findDepartment(@PathVariable Long id) throws DepartmentNotFoundException {
        return departmentService.getDepartment(id);
    }

    @PostMapping("/{id}")
    public DepartmentEntity create(@PathVariable Long id, @Valid @RequestBody FacultyDTO facultyDto) throws Exception {
        System.out.println(facultyDto.getName());
        FacultyEntity faculty = facultyService.getFaculty(id);
        DepartmentEntity department = new DepartmentEntity();
        department.setName(facultyDto.getName());
        department.setFaculty(faculty);
        return departmentService.saveDepartment(department);
    }

    @PutMapping("/{faculty_id}/departments/{id}")
    public DepartmentEntity updateFaculty(@PathVariable Long id, @Valid @RequestBody FacultyDTO facultyDto) throws Exception {
        DepartmentEntity department = departmentService.getDepartment(id);
        department.setName(facultyDto.getName());
        return departmentService.updateDepartment(department);
    }
}
