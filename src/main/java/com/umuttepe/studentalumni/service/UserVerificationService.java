package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.UserVerificationDao;
import com.umuttepe.studentalumni.dao.entity.UserVerificationEntity;
import com.umuttepe.studentalumni.exception.user.UserVerificationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationService {
    @Autowired
    private UserVerificationDao userVerificationDao;

    public UserVerificationEntity checkCode(String code) throws UserVerificationNotFoundException {
        UserVerificationEntity verification = userVerificationDao.findByCode(code);
        if (verification == null) {
            throw new UserVerificationNotFoundException("Kod bulunamadı!");
        }
        return verification;
    }

    public void deleteCode(Long id){
        userVerificationDao.deleteById(id);
    }

    public UserVerificationEntity createCode(UserVerificationEntity verification) {
        return userVerificationDao.save(verification);
    }

}
