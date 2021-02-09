package com.umuttepe.studentalumni.service;

import com.umuttepe.studentalumni.dao.SurveyDao;
import com.umuttepe.studentalumni.dao.entity.SurveyEntity;
import com.umuttepe.studentalumni.dto.survey.SurveyAddDTO;
import com.umuttepe.studentalumni.exception.survey.SurveyConstraintException;
import com.umuttepe.studentalumni.exception.survey.SurveyNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyDao surveyDao;

    public List<SurveyEntity> getSurveys() {
        return surveyDao.findAll();
    }

    public SurveyEntity getSurvey(Integer id) throws SurveyNotFoundException {
        return surveyDao.findById(id)
                .orElseThrow(() -> new SurveyNotFoundException("Anket bulunamadı!"));
    }

    public SurveyEntity addSurvey(SurveyAddDTO survey) throws Exception {
        SurveyEntity newSurvey = new SurveyEntity();
        newSurvey.setName(survey.getName());
        newSurvey.setIsActive(true);
        return surveyDao.save(newSurvey);
    }

    public SurveyEntity updateSurvey(SurveyEntity survey) {
        return surveyDao.save(survey);
    }

    public void delete(Integer id) {
        surveyDao.deleteById(id);
    }
}
