package com.umuttepe.studentalumni.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_apply_files")
public class JobApplyFileEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobApplyFileSQ")
    @SequenceGenerator(name = "jobApplyFileSQ", sequenceName = "job_apply_files_sq")
    private Long id;

    @ManyToOne
    @JsonBackReference
    private JobApplyEntity job_apply;

    @Column(name = "file")
    private String file;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
}
