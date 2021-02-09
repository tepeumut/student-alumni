package com.umuttepe.studentalumni.controller;

import com.umuttepe.studentalumni.config.JwtTokenUtil;
import com.umuttepe.studentalumni.dao.entity.DepartmentEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dao.entity.UserRole;
import com.umuttepe.studentalumni.dao.entity.UserVerificationEntity;
import com.umuttepe.studentalumni.dto.user.*;
import com.umuttepe.studentalumni.exception.department.DepartmentNotFoundException;
import com.umuttepe.studentalumni.exception.user.*;
import com.umuttepe.studentalumni.service.DepartmentService;
import com.umuttepe.studentalumni.service.JwtUserDetailsService;
import com.umuttepe.studentalumni.service.UserVerificationService;
import com.umuttepe.studentalumni.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
public class JwtAuthenticationController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private UserVerificationService userVerificationService;


    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest) throws UserDisabledException, UserBadCredentialsException, UserNotFoundException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        UserEntity user = userDetailsService.findUserByUsername(authenticationRequest.getUsername());
        if (!user.getIsVerified()) {
            throw new UserDisabledException("Lütfen hesabınızı onaylayın.");
        }
        if (!user.getIsActive()) {
            throw new UserDisabledException("Hesabınız kapatılmıştır.");
        }

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRegisterDTO user) throws UserCheckException {
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

        if (user.getRole() == UserRole.ADMIN) {
            FieldError fError = new FieldError("usercheck", "role", "Geçersiz yetki türü.");
            errors.add(fError);
        }
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
        UserEntity status = userDetailsService.save(user);
        String code = UUID.randomUUID().toString();
        UserVerificationEntity verification = new UserVerificationEntity();
        verification.setUser(status);
        verification.setCode(code);
        userVerificationService.createCode(verification);
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(status.getEmail());
            helper.setFrom("test@yazilim.tk");
            helper.setText("Lütfen hesabınızı aşağıdaki link üzerinden onaylayın. <a href='"+env.getProperty("ui.url")+"/email/" + code + "'>"+code+"</a>", true);
            helper.setSubject("Hesap Dogrulama");
        } catch (MessagingException ignore) {}
        mailSender.send(msg);
        return ResponseEntity.ok(status);
    }
    private void authenticate(String username, String password) throws UserDisabledException, UserBadCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException("DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new UserBadCredentialsException("BAD_CREDENTIAL", e);
        }
    }
    @GetMapping("/auth/user")
    public ResponseEntity<?> getUserData(Authentication auth) {
        UserEntity userDetails = userDetailsService
                .loadByUsername(auth.getName());

        return ResponseEntity.ok(new JwtUserResponse(userDetails));
    }

    @GetMapping("/auth/user/profile/{username}")
    public UserEntity getUserProfileData(@PathVariable("username") String username) {
        return userDetailsService
                .loadByUsername(username);
    }

    @PostMapping("/auth/user")
    public UserEntity updateUserProfileData(Authentication auth, @RequestParam(name = "job_status") Boolean job_status, @RequestParam(name = "about") String about, @RequestParam(name = "image", required = false) MultipartFile image) throws UserProfileException {
        UserEntity userDetails = userDetailsService
                .loadByUsername(auth.getName());
        if (image != null) {
            String filename = UUID.randomUUID().toString();
            String fileName = filename + "." + storageService.getExtensionByApacheLib(image.getOriginalFilename());
            storageService.save(image, fileName);
            userDetails.setProfileImage(fileName);
        }
        if (about.isEmpty()) {
            throw new UserProfileException("Hakkımızda yazısı zorunlu!");
        }
        userDetails.setJobStatus(job_status);
        userDetails.setAbout(about);

        return userDetailsService.update(userDetails);
    }

    @PostMapping("/auth/user-personnel")
    public UserEntity updateUserPersonnelData(Authentication auth, @Valid @RequestBody UserPersonnelUpdateDTO userPersonnelUpdateDTO) throws UserProfileException, UserCheckException, DepartmentNotFoundException {
        UserEntity userDetails = userDetailsService.loadByUsername(auth.getName());
        List<FieldError> errors = new ArrayList<>();
        userDetails.setPhone(userPersonnelUpdateDTO.getPhone().replaceAll("\\s+",""));
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher(userDetails.getPhone());
        if (!matcher.matches()) {
            FieldError fError = new FieldError("usercheck", "phone", "Gecersiz telefon formati.");
            errors.add(fError);
        }
        Boolean findUserEmail = userDetailsService.checkUserEmailwithIgnore(userDetails.getId(), userPersonnelUpdateDTO.getEmail());
        Boolean findUserPhone = userDetailsService.checkUserPhonewithIgnore(userDetails.getId(), userPersonnelUpdateDTO.getPhone());
        Boolean findUserUsername = userDetailsService.checkUserUsernamewithIgnore(userDetails.getId(), userPersonnelUpdateDTO.getUsername());

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
        userDetails.setName(userPersonnelUpdateDTO.getName());
        userDetails.setLastName(userPersonnelUpdateDTO.getLastName());
        userDetails.setEmail(userPersonnelUpdateDTO.getEmail());
        userDetails.setUsername(userPersonnelUpdateDTO.getUsername());
        DepartmentEntity department = departmentService.getDepartment(userPersonnelUpdateDTO.getDepartmentId());
        userDetails.setDepartment(department);
        return userDetailsService.update(userDetails);
    }

    @GetMapping("/users/graduation-date")
    public List<UserEntity> users() {
        return userDetailsService.getUsersGraduation();
    }
}