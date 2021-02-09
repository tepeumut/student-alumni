package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.dao.entity.JobEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dao.entity.UserRole;
import com.umuttepe.studentalumni.dao.entity.UserVerificationEntity;
import com.umuttepe.studentalumni.dto.job.JobAddDTO;
import com.umuttepe.studentalumni.dto.user.UserAddDTO;
import com.umuttepe.studentalumni.dto.user.UserEditDTO;
import com.umuttepe.studentalumni.dto.user.UserPasswordEditDTO;
import com.umuttepe.studentalumni.dto.user.UserRegisterDTO;
import com.umuttepe.studentalumni.exception.user.UserCheckException;
import com.umuttepe.studentalumni.exception.user.UserNotFoundException;
import com.umuttepe.studentalumni.service.JwtUserDetailsService;
import com.umuttepe.studentalumni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public List<UserEntity> findAll() {
        return userService.findAllUsers(UserRole.USER);
    }

    @GetMapping("/{id}")
    public UserEntity find(@PathVariable Long id) throws UserNotFoundException {
        return userService.find(id);
    }

    @PostMapping
    public UserEntity add(@Valid @RequestBody UserAddDTO user) throws UserCheckException {
        List<FieldError> errors = new ArrayList<>();
        user.setPhone(user.getPhone().replaceAll("\\s+",""));
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher(user.getPhone());
        if (!matcher.matches()) {
            FieldError fError = new FieldError("usercheck", "phone", "Gecersiz telefon formati.");
            errors.add(fError);
        }
        Boolean findUserEmail = userDetailsService.checkUserEmail(user.getEmail());
        Boolean findUserPhone = userDetailsService.checkUserPhone(user.getPhone());
        Boolean findUserUsername = userDetailsService.checkUserUsername(user.getUsername());

        if (!findUserEmail) {
            FieldError fError = new FieldError("usercheck", "email", "Bu email zaten kullaniliyor.");
            errors.add(fError);
        }
        if (!findUserPhone) {
            FieldError fError = new FieldError("usercheck", "phone", "Bu telefon zaten kullaniliyor.");
            errors.add(fError);
        }
        if (!findUserUsername) {
            FieldError fError = new FieldError("usercheck", "username", "Bu kullanici adi zaten kullaniliyor.");
            errors.add(fError);
        }
        if (!errors.isEmpty()) {
            throw new UserCheckException(errors);
        }
        UserEntity userEntity = userService.save(convertAddToEntity(user));
        return userEntity;
    }

    @PutMapping("/{id}")
    public UserEntity update(@PathVariable Long id,@Valid @RequestBody UserEditDTO user) throws UserNotFoundException, UserCheckException {
        List<FieldError> errors = new ArrayList<>();
        user.setPhone(user.getPhone().replaceAll("\\s+",""));
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher(user.getPhone());
        if (!matcher.matches()) {
            FieldError fError = new FieldError("usercheck", "phone", "Gecersiz telefon formati.");
            errors.add(fError);
        }
        Boolean findUserEmail = userDetailsService.checkUserEmailwithIgnore(id, user.getEmail());
        Boolean findUserPhone = userDetailsService.checkUserPhonewithIgnore(id, user.getPhone());
        Boolean findUserUsername = userDetailsService.checkUserUsernamewithIgnore(id, user.getUsername());

        if (!findUserEmail) {
            FieldError fError = new FieldError("usercheck", "email", "Bu email zaten kullaniliyor.");
            errors.add(fError);
        }
        if (!findUserPhone) {
            FieldError fError = new FieldError("usercheck", "phone", "Bu telefon zaten kullaniliyor.");
            errors.add(fError);
        }
        if (!findUserUsername) {
            FieldError fError = new FieldError("usercheck", "username", "Bu kullanici adi zaten kullaniliyor.");
            errors.add(fError);
        }
        if (!errors.isEmpty()) {
            throw new UserCheckException(errors);
        }
        UserEntity userEntity = userService.find(id);
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhone(user.getPhone());
        userEntity.setIsActive(user.getIsActive());
        userEntity.setGraduationDate(user.getGraduationDate());
        userEntity.setStudentNumber(user.getStudentNumber());
        return userService.save(userEntity);
    }

    @PutMapping("/{id}/passwords")
    public UserEntity updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordEditDTO password) throws UserNotFoundException, UserCheckException {
        UserEntity userEntity = userService.find(id);
        return userService.updatePassword(userEntity, password.getPassword());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) throws UserNotFoundException {
        UserEntity user = userService.find(id);
        if (user.getUsername().equals(authentication.getName())) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
        userService.delete(id);
        return ResponseEntity.ok("ok!");
    }

    private UserEntity convertAddToEntity(UserAddDTO userDto){
        return modelMapper.map(userDto, UserEntity.class);
    }

}
