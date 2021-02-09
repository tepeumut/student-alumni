package com.umuttepe.studentalumni.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "user_verifications")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserVerificationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userVerificationSQ")
    @SequenceGenerator(name = "userVerificationSQ", sequenceName = "user_verifications_sq")
    private Long id;

    @ManyToOne
    private UserEntity user;

    @Column(name = "code")
    private String code;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
}
