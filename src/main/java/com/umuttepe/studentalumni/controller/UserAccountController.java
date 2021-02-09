package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.entity.UserAccountEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dto.user.UserAccountDTO;
import com.umuttepe.studentalumni.exception.user.UserAccountException;
import com.umuttepe.studentalumni.service.UserAccountService;
import com.umuttepe.studentalumni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/user-accounts")
public class UserAccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAccountService accountService;

    @GetMapping("{id}")
    public UserAccountEntity getAccount(Authentication auth, @PathVariable Long id) throws UserAccountException {
        UserEntity user = userService.loadByUsername(auth.getName());
        return accountService.getAccountwithUser(id, user);
    }

    @PostMapping
    public UserAccountEntity addAccount(Authentication auth, @RequestBody @Valid UserAccountDTO accountDTO) {
        UserEntity user = userService.loadByUsername(auth.getName());
        UserAccountEntity account = new UserAccountEntity();
        account.setUser(user);
        account.setIcon(accountDTO.getIcon());
        account.setLink(accountDTO.getLink());
        return accountService.saveAccount(account);
    }

    @PutMapping("{id}")
    public UserAccountEntity updateAccount(Authentication auth, @PathVariable Long id, @RequestBody @Valid UserAccountDTO accountDTO) throws UserAccountException {
        UserEntity user = userService.loadByUsername(auth.getName());
        UserAccountEntity account = accountService.getAccountwithUser(id, user);
        account.setIcon(accountDTO.getIcon());
        account.setLink(accountDTO.getLink());
        return accountService.saveAccount(account);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAccount(Authentication auth, @PathVariable Long id) throws UserAccountException {
        UserEntity user = userService.loadByUsername(auth.getName());
        accountService.getAccountwithUser(id, user);
        accountService.deleteAccount(id);
        return ResponseEntity.ok("ok!");
    }
}
