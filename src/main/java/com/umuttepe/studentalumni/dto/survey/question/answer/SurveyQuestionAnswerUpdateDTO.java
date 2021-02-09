package com.umuttepe.studentalumni.dto.survey.question.answer;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SurveyQuestionAnswerUpdateDTO {
    @NotNull
    @JsonIgnore
    private String answer;
}
