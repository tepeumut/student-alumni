package com.umuttepe.studentalumni.dto.survey.question;

import com.umuttepe.studentalumni.dao.entity.SurveyEntity;
import com.umuttepe.studentalumni.dao.entity.SurveyQuestionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SurveyQuestionAddDTO {

    private Integer surveyId;

    @NotNull
    private String question;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SurveyQuestionStatus type;

    @NotNull
    private String data;
}
