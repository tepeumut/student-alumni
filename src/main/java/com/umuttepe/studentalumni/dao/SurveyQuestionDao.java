package com.umuttepe.studentalumni.dao;

import com.umuttepe.studentalumni.dao.entity.SurveyQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyQuestionDao extends JpaRepository<SurveyQuestionEntity, Integer> {

}
