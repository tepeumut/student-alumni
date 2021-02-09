package com.umuttepe.studentalumni.dto.job;

import com.umuttepe.studentalumni.dao.entity.JobApplyStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplyUpdateDTO {
    private JobApplyStatus status;
}
