package com.umuttepe.studentalumni.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordEditDTO {
    @NotNull(message = "Şifre boş olamaz!")
    @Size(min=8, message = "Şifre en az 8 karakter olmalıdır")
    private String password;
}
