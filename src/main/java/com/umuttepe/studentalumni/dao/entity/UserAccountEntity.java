package com.umuttepe.studentalumni.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_accounts")
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userAccountSQ")
    @SequenceGenerator(name = "userAccountSQ", sequenceName = "user_accounts_sq")
    private Long id;

    @Column(name = "icon")
    private String icon;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JsonBackReference
    private UserEntity user;
}