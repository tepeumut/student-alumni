package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.UserVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationDao extends JpaRepository<UserVerificationEntity, Long> {
    public UserVerificationEntity findByCode(String code);
}
