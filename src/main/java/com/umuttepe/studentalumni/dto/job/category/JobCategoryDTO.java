package com.umuttepe.studentalumni.dto.job.category;

import lombok.*;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobCategoryDTO {
    @NotNull
    private String name;
}
