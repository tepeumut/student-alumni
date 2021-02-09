package com.umuttepe.studentalumni.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "user_jobs")
@AllArgsConstructor
@NoArgsConstructor
public class UserJobEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userJobSQ")
    @SequenceGenerator(name = "userJobSQ", sequenceName = "user_jobs_sq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "position")
    private String position;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonBackReference
    private UserEntity user;
}