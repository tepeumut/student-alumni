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
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_applies")
public class JobApplyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobApplySQ")
    @SequenceGenerator(name = "jobApplySQ", sequenceName = "job_applies_sq")
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    @JsonManagedReference
    private JobEntity job;

    @Column(name = "about")
    private String about;

    @OneToMany
    @JoinColumn(name = "job_apply_id", referencedColumnName = "id")
    private Collection<JobApplyFileEntity> files;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private JobApplyStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
