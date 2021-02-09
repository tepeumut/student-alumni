package com.umuttepe.studentalumni.dto.job;
import com.umuttepe.studentalumni.dao.entity.JobStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JobAddDTO {
    @NotNull
    private String name;

    @NotNull
    private JobStatus job_type;

    @NotNull
    private String short_description;

    @NotNull
    private String content;

    @NotNull
    private String experience;

    @NotNull
    private String workLevel;

    @NotNull
    private String offer_salary;

    @NotNull
    private Long category_id;

}
