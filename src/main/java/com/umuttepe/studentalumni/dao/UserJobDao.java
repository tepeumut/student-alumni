package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dao.entity.UserJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJobDao extends JpaRepository<UserJobEntity, Long> {
    UserJobEntity findByIdAndUser(Long id, UserEntity user);
}
