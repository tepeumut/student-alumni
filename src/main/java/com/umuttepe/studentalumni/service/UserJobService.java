package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.UserJobDao;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dao.entity.UserJobEntity;
import com.umuttepe.studentalumni.exception.user.UserJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserJobService {
    @Autowired
    private UserJobDao userJobDao;

    public UserJobEntity getUserJob(Long id) throws UserJobException {
        return userJobDao.findById(id)
                .orElseThrow(() -> new UserJobException("İş deneyimi bulunamadı!"));
    }

    public UserJobEntity getUserJobwithUser(Long id, UserEntity user) throws UserJobException {
        UserJobEntity job = userJobDao.findByIdAndUser(id, user);
        if (job == null) {
            throw new UserJobException("İş deneyimi bulunamadı!");
        }
        return job;
    }
    public UserJobEntity saveUserJob(UserJobEntity job) {
        return userJobDao.save(job);
    }

    public void deleteUserJob(Long id) {
        userJobDao.deleteById(id);
    }
}
