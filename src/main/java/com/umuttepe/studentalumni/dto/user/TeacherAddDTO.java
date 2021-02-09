package com.umuttepe.studentalumni.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAddDTO {
    @NotNull(message = "Ad boş olamaz!")
    @Size(min = 2, max = 50, message = "Ad 2 ile 50 karakter arasında olmalıdır")
    private String name;

    @NotNull(message = "Soyad boş olamaz!")
    @Size(min = 2, max = 50, message = "Soyad 2 ile 50 karakter arasında olmalıdır")
    private String lastName;

    @NotNull(message = "E-posta boş olamaz!")
    @Email(message = "Geçerli bir e-posta adresi giriniz!")
    @Size(min = 1, max = 255, message = "Email 1 ile 255 karakter arasında olmalıdır")
    private String email;

    @NotNull(message = "Kullanıcı adı boş olamaz!")
    @Size(min = 5, max = 255, message = "Kullanıcı adı 5 ile 255 karakter arasında olmalıdır")
    private String username;

    @NotNull(message = "Telefon boş olamaz!")
    @Size(min= 10, max = 20, message = "Telefon 10 ile 20 karakter arasında olmalıdır")
    private String phone;

    @NotNull(message = "Şifre boş olamaz!")
    @Size(min=8, message = "Şifre en az 8 karakter olmalıdır")
    private String password;

    @NotNull(message = "Durum boş olamaz!")
    private Boolean isActive;
}
