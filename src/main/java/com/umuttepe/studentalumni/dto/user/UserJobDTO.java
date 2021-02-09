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
public class UserJobDTO {
    @NotNull(message = "Firma ad boş olamaz!")
    @Size(min = 2, max = 50, message = "Firma ad 2 ile 50 karakter arasında olmalıdır")
    private String name;

    @NotNull(message = "Pozisyon boş olamaz!")
    @Size(min = 2, max = 100, message = "Pozisyon 2 ile 100 karakter arasında olmalıdır")
    private String position;

    @NotNull(message = "Açıklama boş olamaz!")
    @Size(min = 2, max = 500, message = "Açıklama 2 ile 500 karakter arasında olmalıdır")
    private String description;

    @NotNull(message = "Başlangıç tarihi boş olamaz!")
    private Date startDate;

    private Date endDate;
}