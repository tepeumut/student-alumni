package com.umuttepe.studentalumni.dto.survey.question;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SurveyQuestionDataDTO {
    private Long id;
    private String name;
    private String value;
}
