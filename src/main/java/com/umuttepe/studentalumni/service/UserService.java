package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.UserDao;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dao.entity.UserRole;
import com.umuttepe.studentalumni.exception.user.UserNotFoundException;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    public UserEntity loadByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    public UserEntity find(Long id) throws UserNotFoundException {
        return userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı!"));
    }

    public List<UserEntity> findAllUsers(UserRole role) {
        return userDao.findAllByRole(role);
    }

    public UserEntity save(UserEntity user) {
        user.setIsVerified(true);
        user.setRole(UserRole.USER);
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }
    public UserEntity saveWithRole(UserEntity user, UserRole role) {
        user.setIsVerified(true);
        user.setRole(role);
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    public UserEntity update(UserEntity user) {
        return userDao.save(user);
    }

    public UserEntity updatePassword(UserEntity user, String password) {
        user.setPassword(bcryptEncoder.encode(password));
        return userDao.save(user);
    }

    public void delete(Long id) {
        userDao.deleteById(id);
    }
}
