package com.umuttepe.studentalumni.dto.job;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JobApplyFormAddDTO {

    @NotNull
    private String about;
}
