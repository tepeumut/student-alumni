package com.umuttepe.studentalumni.dto.survey.question;

import com.umuttepe.studentalumni.dao.entity.SurveyQuestionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SurveyQuestionUpdateDTO {

    private Integer id;

    private Integer surveyId;

    private String question;

    @Enumerated(EnumType.ORDINAL)
    private SurveyQuestionStatus type;

    private String data;
}
