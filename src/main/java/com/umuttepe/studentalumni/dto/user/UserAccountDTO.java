package com.umuttepe.studentalumni.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO {
    @NotNull(message = "Hesap adı boş olamaz!")
    @Size(min = 2, max = 50, message = "Hesap adı 2 ile 50 karakter arasında olmalıdır")
    private String icon;

    @NotNull(message = "Hesap link boş olamaz!")
    @Size(min = 2, max = 255, message = "Hesap link 2 ile 100 karakter arasında olmalıdır")
    private String link;
}
