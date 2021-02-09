package com.umuttepe.studentalumni.service;

import com.sun.xml.bind.v2.model.core.ID;
import com.umuttepe.studentalumni.dao.SurveyQuestionDao;
import com.umuttepe.studentalumni.dao.entity.SurveyEntity;
import com.umuttepe.studentalumni.dao.entity.SurveyQuestionEntity;
import com.umuttepe.studentalumni.dto.survey.question.SurveyQuestionAddDTO;
import com.umuttepe.studentalumni.dto.survey.question.SurveyQuestionUpdateDTO;
import com.umuttepe.studentalumni.exception.survey.SurveyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyQuestionService {

    @Autowired
    private SurveyQuestionDao surveyQuestionDao;

    @Autowired
    private SurveyService surveyService;

    public SurveyEntity addQuestion(SurveyQuestionAddDTO surveyQuestionData) throws Exception {
        SurveyEntity survey = surveyService.getSurvey(surveyQuestionData.getSurveyId());
        SurveyQuestionEntity surveyQuestion = new SurveyQuestionEntity();
        surveyQuestion.setQuestion(surveyQuestionData.getQuestion());
        surveyQuestion.setData(surveyQuestionData.getData());
        surveyQuestion.setType(surveyQuestionData.getType());
        survey.getQuestions().add(surveyQuestion);
        return surveyService.updateSurvey(survey);
    }

    public SurveyQuestionEntity getSurveyQuestion(Integer id) throws SurveyNotFoundException {
        return surveyQuestionDao.findById(id)
                .orElseThrow(() -> new SurveyNotFoundException("Soru Bulunamadı!"));
    }

    public SurveyQuestionEntity updateQuestion(SurveyQuestionUpdateDTO surveyQuestionData) throws Exception {
        SurveyQuestionEntity surveyQuestion = getSurveyQuestion(surveyQuestionData.getId());
        surveyQuestion.setQuestion(surveyQuestionData.getQuestion());
        surveyQuestion.setData(surveyQuestionData.getData());
        surveyQuestion.setType(surveyQuestionData.getType());
        return surveyQuestionDao.save(surveyQuestion);
    }

    public void delete(Integer id) {
        surveyQuestionDao.deleteById(id);
    }
}
