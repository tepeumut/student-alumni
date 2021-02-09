package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.UserAccountDao;
import com.umuttepe.studentalumni.dao.entity.UserAccountEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.exception.user.UserAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountDao accountDao;

    public UserAccountEntity getAccountwithUser(Long id, UserEntity user) throws UserAccountException {
        UserAccountEntity account = accountDao.findByIdAndUser(id, user);
        if (account == null) {
            throw new UserAccountException("Hesap bilgisi bulunamadı!");
        }
        return account;
    }
    public UserAccountEntity saveAccount(UserAccountEntity account) {
        return accountDao.save(account);
    }

    public void deleteAccount(Long id) {
        accountDao.deleteById(id);
    }
}
