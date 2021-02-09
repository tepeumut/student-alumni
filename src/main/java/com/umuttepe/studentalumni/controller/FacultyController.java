package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.entity.FacultyEntity;
import com.umuttepe.studentalumni.dto.faculty.FacultyDTO;
import com.umuttepe.studentalumni.exception.faculty.FacultyNotFoundException;
import com.umuttepe.studentalumni.exception.job.JobNotFoundException;
import com.umuttepe.studentalumni.service.FacultyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/faculties")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<FacultyEntity> findAll() {
        return facultyService.getAllFaculties();
    }

    @PostMapping
    public FacultyEntity create(@Valid @RequestBody FacultyDTO facultyDto) throws Exception {
        FacultyEntity faculty = convertAddToEntity(facultyDto);
        return facultyService.saveFaculty(faculty);
    }
    @GetMapping("/{id}")
    public FacultyEntity find(@PathVariable Long id) throws FacultyNotFoundException {
        return facultyService.getFaculty(id);
    }
    @PutMapping("/{id}")
    public FacultyEntity updateFaculty(@PathVariable Long id, @Valid @RequestBody FacultyDTO facultyDto) throws Exception {
        FacultyEntity faculty = facultyService.getFaculty(id);
        faculty.setName(facultyDto.getName());
        return facultyService.updateFaculty(faculty);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long id) throws Exception {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok("Deleted #"+id);
    }
    private FacultyEntity convertAddToEntity(FacultyDTO facultyDTO){
        return modelMapper.map(facultyDTO, FacultyEntity.class);
    }
}
