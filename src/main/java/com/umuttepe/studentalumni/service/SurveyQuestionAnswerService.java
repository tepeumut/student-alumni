package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.SurveyQuestionAnswerDao;
import com.umuttepe.studentalumni.dao.entity.SurveyEntity;
import com.umuttepe.studentalumni.dao.entity.SurveyQuestionAnswerEntity;
import com.umuttepe.studentalumni.dao.entity.SurveyQuestionEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import com.umuttepe.studentalumni.dto.survey.question.SurveyQuestionAddDTO;
import com.umuttepe.studentalumni.dto.survey.question.answer.SurveyQuestionAnswerAddDTO;
import com.umuttepe.studentalumni.dto.survey.question.answer.SurveyQuestionAnswerUpdateDTO;
import com.umuttepe.studentalumni.exception.survey.SurveyQuestionNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyQuestionAnswerService {
    @Autowired
    private SurveyQuestionAnswerDao surveyQuestionAnswerDao;

    public SurveyQuestionAnswerEntity find(SurveyQuestionEntity surveyQuestion, UserEntity user) {
        SurveyQuestionAnswerEntity answer = surveyQuestionAnswerDao.findByQuestionAndUser(surveyQuestion, user);
        if (answer == null) {
            answer = new SurveyQuestionAnswerEntity();
        }
        return answer;
    }

    public List<SurveyQuestionAnswerEntity> findByQuestion(SurveyQuestionEntity surveyQuestion) {
        return surveyQuestionAnswerDao.findByQuestion(surveyQuestion);
    }

    public SurveyQuestionAnswerEntity findById(Long id) throws Exception {
        return surveyQuestionAnswerDao.findById(id)
                .orElseThrow(() -> new Exception("sdasdsdasdasd"));
    }


    public SurveyQuestionAnswerEntity addAnswer(SurveyQuestionAnswerAddDTO questionAnswer, UserEntity user, SurveyQuestionEntity question, SurveyEntity survey) {
        SurveyQuestionAnswerEntity answer = new SurveyQuestionAnswerEntity();
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setSurvey(survey);
        answer.setAnswer(questionAnswer.getAnswer());
        return surveyQuestionAnswerDao.save(answer);
    }

    public SurveyQuestionAnswerEntity updateAnswer(Long answerId, SurveyQuestionAnswerAddDTO answerData) throws Exception {
        SurveyQuestionAnswerEntity oldAnswer = findById(answerId);
        oldAnswer.setAnswer(answerData.getAnswer());
        return surveyQuestionAnswerDao.save(oldAnswer);
    }
}
