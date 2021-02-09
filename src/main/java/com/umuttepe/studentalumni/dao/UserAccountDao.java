package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.UserAccountEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountDao extends JpaRepository<UserAccountEntity, Long> {
    UserAccountEntity findByIdAndUser(Long id, UserEntity user);
}
