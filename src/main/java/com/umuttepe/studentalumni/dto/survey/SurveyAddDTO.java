package com.umuttepe.studentalumni.dto.survey;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SurveyAddDTO {
    @NotNull
    private String name;

}
