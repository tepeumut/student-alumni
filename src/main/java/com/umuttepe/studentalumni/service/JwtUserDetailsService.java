package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.UserDao;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dto.user.UserRegisterDTO;
import com.umuttepe.studentalumni.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public UserEntity findUserByUsername(String username) throws UserNotFoundException {
        UserEntity user = userDao.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public UserEntity save(UserRegisterDTO user) {
        UserEntity newUser = new UserEntity();
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setPhone(user.getPhone());
        newUser.setRole(user.getRole());
        newUser.setIsActive(true);
        newUser.setIsVerified(false);
        newUser.setGraduationDate(user.getGraduationDate());
        return userDao.save(newUser);
    }

    public UserEntity update(UserEntity user) {
        return userDao.save(user);
    }

    public Boolean checkUserUsername(String username) {
        UserEntity user = userDao.findByUsername(username);
        return user == null;
    }

    public Boolean checkUserEmail(String email) {
        UserEntity user = userDao.findByEmail(email);
        return user == null;
    }

    public Boolean checkUserPhone(String phone) {
        UserEntity user = userDao.findByPhone(phone);
        return user == null;
    }

    public Boolean checkUserUsernamewithIgnore(Long id, String username) {
        UserEntity user = userDao.findByUsernameAndIdNot(username, id);
        return user == null;
    }

    public Boolean checkUserEmailwithIgnore(Long id, String email) {
        UserEntity user = userDao.findByEmailAndIdNot(email, id);
        return user == null;
    }

    public Boolean checkUserPhonewithIgnore(Long id, String phone) {
        UserEntity user = userDao.findByPhoneAndIdNot(phone, id);
        return user == null;
    }

    public UserEntity loadByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public List<UserEntity> getUsersGraduation() {
        return userDao.findByOrderByGraduationDateDesc();
    }

}