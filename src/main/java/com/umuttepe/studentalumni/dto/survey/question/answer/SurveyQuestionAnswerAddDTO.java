package com.umuttepe.studentalumni.dto.survey.question.answer;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SurveyQuestionAnswerAddDTO {
    @NotNull
    private String answer;
}

