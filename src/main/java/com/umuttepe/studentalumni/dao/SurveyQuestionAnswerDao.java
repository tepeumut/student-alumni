package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.SurveyEntity;
import com.umuttepe.studentalumni.dao.entity.SurveyQuestionAnswerEntity;
import com.umuttepe.studentalumni.dao.entity.SurveyQuestionEntity;
import com.umuttepe.studentalumni.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SurveyQuestionAnswerDao  extends JpaRepository<SurveyQuestionAnswerEntity, Long> {
    public SurveyQuestionAnswerEntity findByQuestionAndUser(SurveyQuestionEntity question, UserEntity user);
    public List<SurveyQuestionAnswerEntity> findByQuestion(SurveyQuestionEntity question);
}
