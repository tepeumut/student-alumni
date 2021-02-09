package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dao.entity.UserRole;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUsername(String username);
    public UserEntity findByEmail(String email);
    public UserEntity findByPhone(String phone);
    public UserEntity findByUsernameAndIdNot(String username, Long id);
    public UserEntity findByEmailAndIdNot(String email, Long id);
    public UserEntity findByPhoneAndIdNot(String phone, Long id);
    public List<UserEntity> findByOrderByGraduationDateDesc();
    public List<UserEntity> findAllByRole(UserRole role);
}
