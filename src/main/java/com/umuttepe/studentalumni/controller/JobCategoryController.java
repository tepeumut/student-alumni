package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.entity.JobCategoryEntity;
import com.umuttepe.studentalumni.dto.job.category.JobCategoryDTO;
import com.umuttepe.studentalumni.exception.job.JobNotFoundException;
import com.umuttepe.studentalumni.service.JobCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/job-categories")
public class JobCategoryController {
    @Autowired
    private JobCategoryService jobCategoryService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<JobCategoryEntity> findAll() {
        return jobCategoryService.getCategories();
    }

    @PostMapping
    public JobCategoryEntity create(@Valid @RequestBody JobCategoryDTO categoryDto) throws Exception {
        JobCategoryEntity category = convertAddToEntity(categoryDto);
        return jobCategoryService.addCategory(category);
    }
    @GetMapping("/{id}")
    public JobCategoryEntity find(@PathVariable Long id) throws JobNotFoundException {
        return jobCategoryService.getCategory(id);
    }
    @PutMapping("/{id}")
    public JobCategoryEntity updateCategory(@PathVariable Long id, @Valid @RequestBody JobCategoryDTO categoryDto) throws Exception {
        JobCategoryEntity category = jobCategoryService.getCategory(id);
        category.setName(categoryDto.getName());
        return jobCategoryService.updateCategory(category);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws Exception {
        jobCategoryService.deleteCategory(id);
        return ResponseEntity.ok("Deleted #"+id);
    }
    private JobCategoryEntity convertAddToEntity(JobCategoryDTO categoryDTO){
        return modelMapper.map(categoryDTO, JobCategoryEntity.class);
    }
}
